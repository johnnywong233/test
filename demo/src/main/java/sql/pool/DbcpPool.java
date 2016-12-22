package sql.pool;


import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by wajian on 2016/8/23.
 * use apache tomcat dbcp connection pool
 */
public class DbcpPool {

    //singleton
    //http://blog.csdn.net/HappySorry/article/details/50959565
    private static String url = "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf-8";
    private static String user = "root";
    private static String password = "root";
    private static String driverClassName = "com.mysql.jdbc.Driver";
    private static Object obj = new Object();

    private static BasicDataSource bds = null;

    public static Connection getConnectionByPool() {
        if (bds == null) {
            synchronized (obj) {
                if (bds == null) {
                    bds = new BasicDataSource();
                    bds.setDriverClassName(driverClassName);
                    bds.setUrl(url);
                    bds.setUsername(user);
                    bds.setPassword(password);
                    bds.setInitialSize(10);
                    bds.setMaxActive(20);
                    bds.setMinIdle(2);
                }
            }
        }
        try {
            return bds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
