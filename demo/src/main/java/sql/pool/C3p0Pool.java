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
                    //注册驱动
                    dataSource.setDriverClass(driverClassName);
                    //设置数据库url
                    dataSource.setJdbcUrl(url);
                    //设置数据库用户名
                    dataSource.setUser(user);
                    //设置数据库密码
                    dataSource.setPassword(password);
                    //设置连接池最大连接数
                    dataSource.setMaxPoolSize(40);
                    //设置最小连接数
                    dataSource.setMinPoolSize(2);
                    //设置初始连接数
                    dataSource.setInitialPoolSize(10);
                    //设置最大statement缓存数
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
