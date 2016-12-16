package sql.pool;


import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * Created by wajian on 2016/8/23.
 * use apache tomcat dbcp connection pool
 */
public class DbcpPool {

    //单例模式
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
                    //创建数据源对象
                    bds = new BasicDataSource();
                    //设置连接池所需的驱动
                    bds.setDriverClassName(driverClassName);
                    bds.setUrl(url);
                    bds.setUsername(user);
                    bds.setPassword(password);
                    //设置连接池的初始连接数
                    bds.setInitialSize(10);
                    //设置连接池最多可以有多少个活动连接数
                    bds.setMaxActive(20);
                    //设置连接池最少有两个空闲的连接
                    bds.setMinIdle(2);
                    //通过数据源获取连接
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
