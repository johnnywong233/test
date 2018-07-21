package com.johnny.mysql.test;

import com.johnny.mysql.entity.Role;
import com.johnny.mysql.repository.DepartmentRepository;
import com.johnny.mysql.entity.Department;
import com.johnny.mysql.entity.User;
import com.johnny.mysql.repository.RoleRepository;
import com.johnny.mysql.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfiguration.class})
public class MysqlTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    RoleRepository roleRepository;

    @Before
    public void initData() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        departmentRepository.deleteAll();

        Department department = new Department();
        //中文有编码问题，报错：Caused by: java.sql.SQLException: Incorrect string value: '\xE5\xBC\x80\xE5\x8F\x91...' for column 'name' at row 1
        department.setName("R&D");
        departmentRepository.save(department);
        Assert.notNull(department.getId(), "");

        Role role = new Role();
        role.setName("admin");
        roleRepository.save(role);
        Assert.notNull(role.getId(), "");

        User user = new User();
        user.setName("user");
        BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
        user.setPassword(bpe.encode("user"));
        user.setCreateDate(new Date());
        user.setDepartment(department);
        userRepository.save(user);
        Assert.notNull(user.getId(), "");
    }

    @Test
    public void insertUserRoles() {
        User user = userRepository.findByName("user");
        Assert.notNull(user, "");

        List<Role> roles = roleRepository.findAll();
        Assert.notNull(roles, "");
        user.setRoles(roles);
        userRepository.save(user);
    }
}
