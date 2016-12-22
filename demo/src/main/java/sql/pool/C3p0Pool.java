package sql.pool;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Author: Johnny
 * Date: 2016/11/9
 * Time: 0:38
 */
public class C3p0Pool {

    static ComboPooledDataSource dataSource = null;
    private static Connection connection;
    private static String url = "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf-8";
    private static String user = "root";
    private static String password = "root";
    private static String driverClassName = "com.mysql.jdbc.Driver";
    private static Object obj = new Object();

    public static void initc3p0DataSource() {
        synchronized (obj) {
            if (dataSource == null) {
                dataSource = new ComboPooledDataSource();
                try {
                    dataSource.setDriverClass(driverClassName);
                    dataSource.setJdbcUrl(url);
                    dataSource.setUser(user);
                    dataSource.setPassword(password);
                    dataSource.setMaxPoolSize(40);
                    dataSource.setMinPoolSize(2);
                    dataSource.setInitialPoolSize(10);
                    dataSource.setMaxStatements(20);
                } catch (PropertyVetoException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Connection getConnectionFromc3p0() {
        if (dataSource == null) {
            initc3p0DataSource();
        }
        if (dataSource != null) {
            try {
                return dataSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
