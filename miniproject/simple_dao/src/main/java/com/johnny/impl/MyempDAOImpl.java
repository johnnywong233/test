package com.johnny.impl;

import com.johnny.dao.IMyempDAO;
import com.johnny.vo.Myemp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MyempDAOImpl implements IMyempDAO {
    private final Connection conn;
    private PreparedStatement pstmt = null;

    public MyempDAOImpl(Connection conn) {//构造方法取得与数据库连接
        this.conn = conn;
    }

    @Override
    public boolean doCreate(Myemp emp) throws Exception {
        boolean flag = false;
        String sql = "INSERT INTO myemp(username,password) VALUES (?,?)";
        this.pstmt = this.conn.prepareStatement(sql);
        this.pstmt.setString(1, emp.getUsername());
        this.pstmt.setString(2, emp.getPassword());
        if (this.pstmt.executeUpdate() > 0) {
            flag = true;
        }
        this.pstmt.close();
        return flag;
    }

    @Override
    public List<Myemp> findAll(String keyWord) throws Exception {
        List<Myemp> all = new ArrayList<>();     //定义集合接收数据
        String sql = "SELECT id,username,password FROM myemp WHERE username LIKE ? OR password LIKE ?";
        this.pstmt = this.conn.prepareStatement(sql);
        this.pstmt.setString(1, "%" + keyWord + "%");
        this.pstmt.setString(2, "%" + keyWord + "%");
        ResultSet rs = this.pstmt.executeQuery();
        Myemp emp;
        while (rs.next()) {  //依次将数据就存入VO,向集合增加
            emp = new Myemp();
            emp.setId(rs.getInt(1));
            emp.setUsername(rs.getString(2));
            emp.setPassword(rs.getString(3));
            all.add(emp);
        }
        this.pstmt.close();
        return all;
    }

}
