package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

public class ConnectionManager {

    /**
     * 使用单件模式访问连接池，客户端只能通过ConnecitonManager.getInstance()得到一个实例。
     * 当客户端关闭时，应该调用release()方法关闭所有的数据库连接，并做好其他的清理工作。
     */
    private static final int TIME_BETWEEN_RETRIES = 500; // 0.5 second

    // static variable
    static private ConnectionManager instance = null; // The single instance

    // instance variable
    private DBConnectionPool pool = null;

    /**
     * 私有构造方法 初始化数据库的连接
     */
    private ConnectionManager() {
        DBOptions option = new DBOptions();
        String driverClassName = option.getDriverClassName();
        try {
            Class.forName(driverClassName).newInstance();
        } catch (Exception e) {
            System.out.println("fatal ERROR:   ConnectionManager: Unable to load driver = "
                    + driverClassName);
        }

        String url = option.getDatabaseURL();
        String user = option.getDatabaseUser();
        String password = option.getDatabasePassword();
        int maxConnection = option.getMaxConnection();

        //always new the pool because pool is an instance variable
        pool = new DBConnectionPool(url, user, password, maxConnection);
    }

    /**
     * 返回一个单独的实例，如果这个方法是第一次被调用就创建一个新的实例
     *
     * @return ConnectionManager The single instance.
     */
    public static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    /**
     * 返回一个连接。
     * 如果没有可以使用的连接，并且连接数还没有达到最大，就创建一个新的连接
     *
     * @return Connection The connection or null
     */
    Connection getConnection() {
        return pool.getConnection();
    }

    /**
     * 返回一个连接。
     * 如果没有可以使用的连接，并且连接数还没有达到最大，就创建一个新的连接；
     * 如果连接数达到了最大值，按指定的时间等待，如果这个时间内有连接释放了，就返回一个连接，
     * 如果没有连接被释放，就返回null
     *
     * @param time The number of milliseconds to wait
     * @return Connection The connection or null
     */
    Connection getConnection(long time) {
        return pool.getConnection(time);
    }

    /**
     * @return true if the pool is empty and balance false if the pool has
     * returned some connection to outside
     */
    boolean release() {
        return pool.release();
    }

    /**
     * 连接池使用内部类来实现
     */
    class DBConnectionPool {
        private int checkedOut = 0;

        private Vector freeConnections = new Vector();

        private int maxConn = 0;

        private String password = null;

        private String URL = null;

        private String user = null;

        /**
         * Creates new connection pool. NOTE: new an instance of this class is
         * lightweight since it does not create any connections
         *
         * @param URL      The JDBC URL for the database
         * @param user     The database user, or null
         * @param password The database user password, or null
         * @param maxConn  The maximal number of connections, or 0 for no limit
         */
        public DBConnectionPool(String URL, String user, String password,
                                int maxConn) {
            this.URL = URL;
            this.user = user;
            this.password = password;
            this.maxConn = maxConn;
        }

        /**
         * Checks in a connection to the pool. Notify other Threads that may be
         * waiting for a connection.
         *
         * @param con The connection to check in
         */
        synchronized void freeConnection(Connection con) {
            if (con != null) {//make sure that the connection is not null
                if (checkedOut <= 0) {
                    try {
                        System.out.println("ConnectionManager: about to close the orphan connection.");
                        con.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    freeConnections.addElement(con);
                    checkedOut--;
                    notifyAll();
                }
            }
        }

        /**
         * Checks out a connection from the pool. If no free connection is
         * available, a new connection is created unless the max number of
         * connections has been reached. If a free connection has been closed by
         * the database, it's removed from the pool and this method is called
         * again recursively.
         */
        synchronized Connection getConnection() {
            Connection con = null;

            while ((freeConnections.size() > 0) && (con == null)) {
                con = (Connection) freeConnections.firstElement();
                freeConnections.removeElementAt(0);
                try {
                    if (con.isClosed()) {
                        System.out.println("Removed bad connection in DBConnectionPool.");
                        con = null; // to make the while loop to continue
                    }
                } catch (SQLException e) {
                    con = null; // to make the while loop to continue
                }
            } // while

            // cannot get any connection from the pool
            if (con == null) {
                if (maxConn == 0 || checkedOut < maxConn) {
                    // maxConn = 0 means unlimited connections
                    con = newConnection();
                }
            }
            if (con != null) {
                checkedOut++;
            }
            return con;
        }

        /**
         * 返回一个连接。
         * 如果没有可以使用的连接，并且连接数还没有达到最大，就创建一个新的连接；
         * 如果连接数达到了最大值，按指定的时间等待，如果这个时间内有连接释放了，就返回一个连接，
         * 如果没有连接被释放，就返回null
         *
         * @param timeout The timeout value in milliseconds
         */
        Connection getConnection(long timeout) {
            long startTime = System.currentTimeMillis();
            Connection con;
            while ((con = getConnection()) == null) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime >= timeout) {
                    return null;
                }
                long timeToWait = timeout - elapsedTime;
                if (timeToWait > TIME_BETWEEN_RETRIES)
                    timeToWait = TIME_BETWEEN_RETRIES;
                try {
                    Thread.sleep(timeToWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return con;
        }

        /**
         * close all connection
         *
         * @return true if the pool is empty and balance false if the pool has
         * returned some connection to outside
         */
        synchronized boolean release() {
            boolean retValue = true;
            Enumeration allConnections = freeConnections.elements();
            while (allConnections.hasMoreElements()) {
                Connection con = (Connection) allConnections.nextElement();
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println("Cannot close connection in DBConnectionPool.");
                }
            }
            freeConnections.removeAllElements();
            if (checkedOut != 0) {
                retValue = false;
                System.out.println("ConnectionManager: the built-in connection pool is not balanced.");
            }
            checkedOut = 0;
            return retValue;
        }

        //create a database connection with user/password
        private synchronized Connection newConnection() {
            Connection con;
            try {
                //login without a user
                if (user == null) {
                    con = DriverManager.getConnection(URL);
                } else {
                    con = DriverManager.getConnection(URL, user, password);
                }
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println(
                        "Cannot create a new connection in DBConnectionPool. URL = "
                                + URL + e.getMessage());
                return null;
            }
            return con;
        }
    }
}
