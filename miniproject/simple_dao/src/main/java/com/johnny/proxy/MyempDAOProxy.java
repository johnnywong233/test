package com.johnny.proxy;

import com.johnny.dao.IMyempDAO;
import com.johnny.impl.MyempDAOImpl;
import com.johnny.util.DatabaseConnection;
import com.johnny.vo.Myemp;

import java.util.List;

public class MyempDAOProxy implements IMyempDAO {
    private final DatabaseConnection dbc;
    private final IMyempDAO dao;

    public MyempDAOProxy() throws Exception {//构造方法中实例化连接与实例化DAO对象
        this.dbc = new DatabaseConnection();
        this.dao = new MyempDAOImpl(this.dbc.getConnection());
    }

    @Override
    public boolean doCreate(Myemp emp) throws Exception {
        boolean flag;
        try {
            flag = this.dao.doCreate(emp);   //调用真实类的doCreate方法
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            this.dbc.close();
        }
        return flag;
    }

    @Override
    public List<Myemp> findAll(String keyWord) throws Exception {
        List<Myemp> all;
        try {
            all = this.dao.findAll(keyWord); //调用真实类的findAll方法
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            this.dbc.close();
        }
        return all;
    }

}
