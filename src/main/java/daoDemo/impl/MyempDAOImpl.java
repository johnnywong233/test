package daoDemo.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import daoDemo.dao.IMyempDAO;
import daoDemo.vo.Myemp;

public class MyempDAOImpl implements IMyempDAO {
	private Connection conn = null;
    private PreparedStatement pstmt = null;
 
    public MyempDAOImpl(Connection conn) {//构造方法取得与数据库连接
       this.conn = conn;
    }
 
    public boolean doCreate(Myemp emp) throws Exception {
       boolean flag = false;
       String sql = "INSERT INTO myemp(username,password) VALUES (?,?)"; //SQL插入语句
       this.pstmt = this.conn.prepareStatement(sql);                     //预编译
       this.pstmt.setString(1, emp.getUsername());                       //设置用户名
       this.pstmt.setString(2, emp.getPassword());                       //设置密码
       if (this.pstmt.executeUpdate() > 0) { //如果有更新记录 标记为true
           flag = true;
       }
       this.pstmt.close();  //关闭打开的操作
       return flag;
    }
 
    public List<Myemp> findAll(String keyWord) throws Exception {
       List<Myemp> all = new ArrayList<Myemp>();     //定义集合接收数据
       String sql = "SELECT id,username,password FROM myemp WHERE username LIKE ? OR password LIKE ?"; //SQL查询语句
       this.pstmt = this.conn.prepareStatement(sql); //预编译
       this.pstmt.setString(1, "%" + keyWord + "%"); //设置用户名 模糊查询
       this.pstmt.setString(2, "%" + keyWord + "%"); //设置密码 模糊查询
       ResultSet rs = this.pstmt.executeQuery();     //接收数据
       Myemp emp = null;
       while (rs.next()) {  //依次将数据就存入VO,向集合增加
           emp = new Myemp();
           emp.setId(rs.getInt(1));
           emp.setUsername(rs.getString(2));
           emp.setPassword(rs.getString(3));
           all.add(emp);
       }
       this.pstmt.close();  //关闭打开的操作
       return all;          //返回结果
    }

}
