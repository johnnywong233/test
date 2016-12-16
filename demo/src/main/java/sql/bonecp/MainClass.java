package sql.bonecp;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by johnny on 2016/10/2.
 * test bonecp
 */
public class MainClass {
    //http://blog.csdn.net/u012540337/article/details/48178047
    public static void main(String[] args) {
        BoneCP connectionPool;
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            //设置连接池配置信息
            BoneCPConfig config = new BoneCPConfig();
            //数据库的JDBC URL
            config.setJdbcUrl("jdbc:mysql:///jooq");
            config.setUsername("root");
            config.setPassword("root");
            config.setMinConnectionsPerPartition(5);
            config.setMaxConnectionsPerPartition(10);
            //
            config.setPartitionCount(1);
            //设置数据库连接池
            connectionPool = new BoneCP(config);
            //从数据库连接池获取一个数据库连接
            connection = connectionPool.getConnection(); // fetch a connection

            if (connection != null) {
                System.out.println("Connection successful!");
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM customer");
                while (rs.next()) {
                    System.out.println(rs.getInt(1) + ":" + rs.getString("firstname") + "," + rs.getString("lastname"));
                }
            }
            connectionPool.shutdown();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
