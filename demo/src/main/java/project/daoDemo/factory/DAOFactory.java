package project.daoDemo.factory;

import project.daoDemo.dao.IMyempDAO;
import project.daoDemo.proxy.MyempDAOProxy;

public class DAOFactory {
    public static IMyempDAO getIEmpDAOInstance() throws Exception { //取得DAO接口实例
        return new MyempDAOProxy(); //取得代理类的实例
    }
}
