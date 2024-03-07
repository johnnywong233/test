package project.mvcDemo.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbSession {
    private Connection con = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    /**
     * 开启数据库会话
     */
    public void open() throws DbSessionException {
        if (con == null) {
            try {
                con = DbResourceManager.getConnection();
            } catch (Exception e) {
                throw new DbSessionException("创建会话失败", e);
            }
        }
    }

    /**
     * 获得与数据库会话绑定的连接
     */
    public Connection getConnection() {
        return con;
    }

    /**
     * 关闭数据库会话
     */
    public void close() throws DbSessionException {
        DbResourceManager.close(rs);
        rs = null;
        DbResourceManager.close(stmt);
        stmt = null;
        DbResourceManager.close(con);
        con = null;
    }

    /**
     * 开启事务
     */
    public void beginTx() {
        try {
            if (con != null && !con.isClosed()) {
                con.setAutoCommit(false);
            }
        } catch (SQLException e) {
            throw new RuntimeException("开启事务失败", e);
        }
    }

    /**
     * 提交事务
     */
    public void commitTx() throws DbSessionException {
        try {
            if (con != null && !con.isClosed()) {
                con.commit();
            }
        } catch (SQLException e) {
            throw new DbSessionException("提交事务失败", e);
        }
    }

    /**
     * 回滚事务
     */
    public void rollbackTx() throws DbSessionException {
        try {
            if (con != null && !con.isClosed()) {
                con.rollback();
            }
        } catch (SQLException e) {
            throw new DbSessionException("回滚事务失败", e);
        }
    }

    /**
     * 执行更新语句
     *
     * @param sql    SQL语句
     * @param params 替换SQL语句中占位符的参数
     * @return 多少行受影响
     */
    public DbResult executeUpdate(String sql, Object... params) throws DbSessionException {
        try {
            boolean isInsert = sql.trim().startsWith("insert");
            if (isInsert) {
                stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            } else {
                stmt = con.prepareStatement(sql);
            }
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            int affectedRows = stmt.executeUpdate();
            Serializable generatedKey = null;
            if (isInsert) {
                rs = stmt.getGeneratedKeys();
                generatedKey = rs.next() ? (Serializable) rs.getObject(1) : generatedKey;
            }
            return new DbResult(affectedRows, generatedKey);
        } catch (SQLException e) {
            throw new DbSessionException("Unexpected exception occurred while execute update", e);
        }
    }

    /**
     * 执行查询语句
     *
     * @param sql    SQL语句
     * @param params 替换SQL语句中占位符的参数
     * @return 结果集(游标)
     */
    public ResultSet executeQuery(String sql, Object... params) throws DbSessionException {
        try {
            stmt = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new DbSessionException("Unexpected exception occurred while execute query", e);
        }
        return rs;
    }
}
