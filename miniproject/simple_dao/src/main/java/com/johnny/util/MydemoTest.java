package com.johnny.util;

import com.johnny.factory.DAOFactory;
import com.johnny.vo.Myemp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

//http://huangxiniu.iteye.com/blog/1717196
public class MydemoTest {
    //TODO

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void doCreate() throws Exception {
        Myemp emp = new Myemp();  //实例化VO对象
        emp.setUsername("admin");
        emp.setPassword("123456");
        System.out.println(DAOFactory.getIEmpDAOInstance().doCreate(emp));
    }

    @Test
    public void findAll() throws Exception {
        List<Myemp> all = DAOFactory.getIEmpDAOInstance().findAll("");
        for (Myemp emp : all) {
            System.out.println("username: " + emp.getUsername() + "password: "
                    + emp.getPassword());
        }
    }
}
