package sql.pool;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Properties;

/**
 * Created by johnny on 2016/8/23.
 * 使用dbcp连接池技术管理数据库连接
 */
public class DbcpPoolDemo {

    private static BasicDataSource dbcp;

    private static ThreadLocal<Connection> tl;

    static {
        try {
            Properties prop = new Properties();

            InputStream is = DbcpPoolDemo.class.getClassLoader().getResourceAsStream("sql/pool/db.properties");

            prop.load(is);
            is.close();

            dbcp = new BasicDataSource();

            dbcp.setDriverClassName(prop.getProperty("jdbc.driver"));
            dbcp.setUrl(prop.getProperty("jdbc.url"));
            dbcp.setUsername(prop.getProperty("jdbc.user"));
            dbcp.setPassword(prop.getProperty("jdbc.password"));
            dbcp.setInitialSize(Integer.parseInt(prop.getProperty("initsize")));
            // 连接池允许的最大连接数
            dbcp.setMaxTotal(Integer.parseInt(prop.getProperty("maxactive")));
            dbcp.setMaxWait(Duration.ofMillis(Long.parseLong(prop.getProperty("maxwait"))));
            dbcp.setMinIdle(Integer.parseInt(prop.getProperty("minidle")));
            dbcp.setMaxIdle(Integer.parseInt(prop.getProperty("maxidle")));
            tl = new ThreadLocal<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = dbcp.getConnection();
        tl.set(conn);
        return conn;
    }

    public static void closeConnection() {
        try {
            Connection conn = tl.get();
            if (conn != null) {
                /*
                 * 通过连接池获取的Connection的close()方法实际上并没有将连接关闭，而是将该链接归还。
                 */
                conn.close();
                tl.remove();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * test connection success or not
     * <a href="http://www.cnblogs.com/liuhongfeng/p/4171785.html">...</a>
     */
    public static void main(String[] args) throws SQLException {
        System.out.println(getConnection());
    }
}
