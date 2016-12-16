package project.daoDemo.proxy;

import java.util.List;

import project.daoDemo.dao.IMyempDAO;
import project.daoDemo.impl.MyempDAOImpl;
import project.daoDemo.util.DatabaseConnection;
import project.daoDemo.vo.Myemp;

public class MyempDAOProxy implements IMyempDAO {
	private DatabaseConnection dbc = null; //定义数据库连接类
    private IMyempDAO dao = null;          //声明DAO
 
    public MyempDAOProxy() throws Exception {//构造方法中实例化连接与实例化DAO对象
       this.dbc = new DatabaseConnection();  //连接数据库
       this.dao = new MyempDAOImpl(this.dbc.getConnection());//实例化真实类
    }
 
    public boolean doCreate(Myemp emp) throws Exception {
       boolean flag = false;
       try {
           flag = this.dao.doCreate(emp);   //调用真实类的doCreate方法
       } catch (Exception e) {
           throw e;
       } finally {
           this.dbc.close();                //关闭数据库连接
       }
       return flag;
    }
 
    public List<Myemp> findAll(String keyWord) throws Exception {
       List<Myemp> all = null;
       try {
           all = this.dao.findAll(keyWord); //调用真实类的findAll方法
       } catch (Exception e) {
           throw e;
       } finally {
           this.dbc.close();                //关闭数据库连接
       }
       return all;
    }

}
