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
    private static final String URL = "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf-8";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final Object OBJ = new Object();

    public static void initC3p0DataSource() {
        synchronized (OBJ) {
            if (dataSource == null) {
                dataSource = new ComboPooledDataSource();
                try {
                    dataSource.setDriverClass(DRIVER_CLASS_NAME);
                    dataSource.setJdbcUrl(URL);
                    dataSource.setUser(USER);
                    dataSource.setPassword(PASSWORD);
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

    public static Connection getConnectionFromC3p0() {
        if (dataSource == null) {
            initC3p0DataSource();
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
