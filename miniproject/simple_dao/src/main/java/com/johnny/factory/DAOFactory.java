package com.johnny.factory;

import com.johnny.dao.IMyempDAO;
import com.johnny.proxy.MyempDAOProxy;

public class DAOFactory {
    public static IMyempDAO getIEmpDAOInstance() throws Exception { //取得DAO接口实例
        return new MyempDAOProxy(); //取得代理类的实例
    }
}
