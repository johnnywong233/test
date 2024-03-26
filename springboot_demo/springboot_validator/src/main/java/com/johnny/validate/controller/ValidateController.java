package com.johnny.validate.controller;

import com.johnny.validate.model.Book;
import com.johnny.validate.model.Person;
import com.johnny.validate.model.User;
import com.johnny.validate.model.view.PersonAddView;
import com.johnny.validate.model.view.PersonModifyView;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试User 部分参数校验
 */
@RestController
@Validated
public class ValidateController {
    /**
     * 新增User
     * 使用默认校验规则
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void addUser(@RequestBody @Valid User user) {
        System.out.println("传入的用户信息是：" + user.toString());
    }

    /**
     * 添加一个Person对象
     * 此处启用PersonAddView 这个验证规则
     * 备注：此处@Validated(PersonAddView.class) 表示使用PersonAndView这套校验规则，若使用@Valid 则表示使用默认校验规则，
     * 若两个规则同时加上去，则只有第一套起作用
     */
    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public void addPerson(@RequestBody @Validated({PersonAddView.class, Default.class}) Person person) {
        System.out.println(person.toString());
    }

    /**
     * 修改Person对象
     * 此处启用PersonModifyView 这个验证规则
     */
    @RequestMapping(value = "/person", method = RequestMethod.PUT)
    public void modifyPerson(@RequestBody @Validated(value = {PersonModifyView.class}) Person person) {
        System.out.println(person.toString());
    }

    /**
     * 添加Book对象
     */
    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public void addBook(@RequestBody @Valid Book book) {
        System.out.println(book.toString());
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String paramCheck(@Length(min = 10) @RequestParam String name) {
        System.out.println(name);
        return null;
    }
}