package com.johnny.util;

import com.johnny.factory.DAOFactory;
import com.johnny.vo.Myemp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MydemoTest {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
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
            System.out.println("username: " + emp.getUsername() + "password: " + emp.getPassword());
        }
    }
}
