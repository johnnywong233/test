package sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {
	/**
     * 使用Statement实现验证用户名密码是否存在的方法
     * 
     * @param username
     * @param password
     */
    public void login(String username, String password) {

        // Statement
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        // 定义sql语句，用来查询用户名和密码
        String sql = null;

        try {
            sql = "select * from users where NAME = '" + username
                    + "' and PWD= '" + password + "'";

            // 检查一下sql语句是否拼写正确
            System.out.println(sql);

            // 获得数据库的连接
            con = DBUtility.getConnection();

            stmt = con.createStatement();

            // 执行sql语句
            rs = stmt.executeQuery(sql);

            // 进行结果的遍历，并给出相应的提示
            if (rs.next()) {
                System.out.println("登录成功！");
            } else {
                System.out.println("登录失败！");
            }

        } catch (SQLException e) {

            System.out.println("数据库访问异常!");
            throw new RuntimeException(e);

        } finally {

            // 最后关闭一下资源
            if (con != null) {
                DBUtility.closeConnection(con);
            }
        }
    }
}
