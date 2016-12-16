package project.daoDemo.util;


import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.daoDemo.factory.DAOFactory;
import project.daoDemo.vo.Myemp;

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
		emp.setUsername("admin"); //设置用户名
		emp.setPassword("123456");//设置密码
		System.out.println(DAOFactory.getIEmpDAOInstance().doCreate(emp));//输出结果
	}

	@Test
	public void findAll() throws Exception {
		List<Myemp> all = DAOFactory.getIEmpDAOInstance().findAll(""); //查询
		Iterator<Myemp> iter = all.iterator(); //迭代操作
		while (iter.hasNext()) {               //依次输出对象里的内容
			Myemp emp = iter.next();
			System.out.println("username: " + emp.getUsername() + "password: "
					+ emp.getPassword());
		}
	}
}
