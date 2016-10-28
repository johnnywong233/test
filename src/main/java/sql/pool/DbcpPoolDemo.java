package sql.pool;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by wajian on 2016/8/23.
 * 使用连接池技术管理数据库连接
 */
public class DbcpPoolDemo {

    //数据库连接池
    private static BasicDataSource dbcp;

    //为不同线程管理连接
    private static ThreadLocal<Connection> tl;

    //通过配置文件来获取数据库参数
    static{
        try{
            Properties prop = new Properties();

            InputStream is = DbcpPoolDemo.class.getClassLoader().getResourceAsStream("sql/pool/db.properties");

            prop.load(is);
            is.close();

            //一、初始化连接池
            dbcp = new BasicDataSource();

            //设置驱动 (Class.forName())
            dbcp.setDriverClassName(prop.getProperty("jdbc.driver"));
            //设置url
            dbcp.setUrl(prop.getProperty("jdbc.url"));
            //设置数据库用户名
            dbcp.setUsername(prop.getProperty("jdbc.user"));
            //设置数据库密码
            dbcp.setPassword(prop.getProperty("jdbc.password"));
            //初始连接数量
            dbcp.setInitialSize(Integer.parseInt(prop.getProperty("initsize")));
            //连接池允许的最大连接数
            dbcp.setMaxActive(Integer.parseInt(prop.getProperty("maxactive")));
            //设置最大等待时间
            dbcp.setMaxWait(Integer.parseInt(prop.getProperty("maxwait")));
            //设置最小空闲数
            dbcp.setMinIdle(Integer.parseInt(prop.getProperty("minidle")));
            //设置最大空闲数
            dbcp.setMaxIdle(Integer.parseInt(prop.getProperty("maxidle")));
            //初始化线程本地
            tl = new ThreadLocal<Connection>();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * get database connection
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
    /*
     * 通过连接池获取一个空闲连接
     */
        Connection conn = dbcp.getConnection();
        tl.set(conn);
        return conn;
    }


    /**
     * close database connection
     */
    public static void closeConnection(){
        try{
            Connection conn = tl.get();
            if(conn != null){
            /*
             * 通过连接池获取的Connection
             * 的close()方法实际上并没有将
             * 连接关闭，而是将该链接归还。
             */
                conn.close();
                tl.remove();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * test connection success or not
     * @param args
     * @throws SQLException
     * http://www.cnblogs.com/liuhongfeng/p/4171785.html
     */
    public static void main(String[] args) throws SQLException {
        System.out.println(getConnection());
    }
}
