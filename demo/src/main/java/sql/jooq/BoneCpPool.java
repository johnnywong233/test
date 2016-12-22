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

    static {
        // load JDBC drver
        try {
            Class.forName("com.mysql.jdbc.Driver");
            boneCPConfig = new BoneCPConfig();
            String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/jooq";
            boneCPConfig.setJdbcUrl(jdbcUrl);
            boneCPConfig.setUser("root");
            boneCPConfig.setPassword("root");
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
