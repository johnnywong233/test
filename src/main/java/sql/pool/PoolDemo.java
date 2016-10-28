package sql.pool;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by wajian on 2016/8/23.
 */
public class PoolDemo {

    //http://blog.csdn.net/HappySorry/article/details/50959565
    private static Connection connection;
    private static String url="jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf-8";
    private static String user = "root";
    private static String password="root";
    private static String driverClassName="com.mysql.jdbc.Driver";
    private static Object obj=new Object();



    //数据源对象
    private static BasicDataSource bds = null;
    public static Connection getConnectionByPool(){
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
            return  null;
        }
    }



    public static void initc3p0DataSource(){
        synchronized (obj){
            if (dataSource == null){
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
    public static Connection getConnectionFromc3p0(){
        if (dataSource == null){
            initc3p0DataSource();
        }
        if (dataSource != null){
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
