package sql.pool;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by johnny on 2016/8/23.
 * use apache tomcat dbcp connection pool
 */
public class DbcpPool {

    //singleton
    //http://blog.csdn.net/HappySorry/article/details/50959565
    private static final String URL = "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf-8";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final Object OBJ = new Object();

    private static volatile BasicDataSource bds = null;

    public static Connection getConnectionByPool() {
        if (bds == null) {
            synchronized (OBJ) {
                if (bds == null) {
                    bds = new BasicDataSource();
                    bds.setDriverClassName(DRIVER_CLASS_NAME);
                    bds.setUrl(URL);
                    bds.setUsername(USER);
                    bds.setPassword(PASSWORD);
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
