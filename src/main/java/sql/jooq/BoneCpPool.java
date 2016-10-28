package sql.jooq;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by johnny on 2016/10/2.
 * use third-party connection pool bonecp to manage database connection
 */
public class BoneCpPool {
    private static BoneCP boneCp = null;
    private static BoneCPConfig boneCPConfig = null;
    // 静态代码块加载配置文件
    static {
        // 加载JDBC驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");// 注册数据库
            boneCPConfig = new BoneCPConfig();// bonecp数据库连接池配置
            String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/jooq";
            boneCPConfig.setJdbcUrl(jdbcUrl);
            boneCPConfig.setUser("root");
            boneCPConfig.setPassword("root");
            // 数据库连接池的最小、最大连接数
            boneCPConfig.setMinConnectionsPerPartition(5);
            boneCPConfig.setMaxConnectionsPerPartition(10);
            boneCPConfig.setPartitionCount(1);
            // System.out.println("boneCPConfig"+boneCPConfig);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //get connection pool
    static BoneCP getBoneCP() {
        try {
            boneCp = new BoneCP(boneCPConfig);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boneCp;
    }

    //get connection
    static Connection getConnection(BoneCP boneCpP) {
        if (boneCpP != null) {
            try {
                return boneCpP.getConnection();
            } catch (SQLException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    //close connection pool
    public static void closeBoneCP(BoneCP bc) {
        bc.close();
    }

    //close connection
    public static void closeConnection(Connection con) throws SQLException {
        con.close();
    }

    //get the context for connection
    public static DSLContext getContext(Connection conDsl) {
        return DSL.using(conDsl);
    }
}
