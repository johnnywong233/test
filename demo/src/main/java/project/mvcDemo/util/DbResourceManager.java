package project.mvcDemo.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DbResourceManager {
    // 最好的做法是将配置保存到配置文件中(可以用properteis文件或XML文件)
    private static final String JDBC_DRV = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hw";
    private static final String JDBC_UID = "root";
    private static final String JDBC_PWD = "123456";

    private static Driver driver = null;
    private static Properties info = new Properties();

    private DbResourceManager() {
        throw new AssertionError();
    }

    static {
        try {
            loadDriver();
            info.setProperty("user", JDBC_UID);
            info.setProperty("password", JDBC_PWD);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setDriver(Driver _driver) {
        driver = _driver;
    }

    private static void loadDriver() throws Exception {
        driver = (Driver) Class.forName(JDBC_DRV).newInstance();
        DriverManager.registerDriver(driver);
    }

    /**
     * get Connection
     *
     * @return 连接对象
     * @throws Exception 无法加载驱动或无法建立连接时将抛出异常
     */
    public static Connection getConnection() throws Exception {
        if (driver == null) {
            loadDriver();
        }
        return driver.connect(JDBC_URL, info);
    }

    /**
     * close ResultSet
     */
    public static void close(ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * close Statement
     */
    public static void close(Statement stmt) throws SQLException {
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * close Connection
     */
    public static void close(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注销驱动
     *
     * @throws SQLException
     */
    public static void unloadDriver() throws SQLException {
        if (driver != null) {
            DriverManager.deregisterDriver(driver);
            driver = null;
        }
    }
}
