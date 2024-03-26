package com.johnny.validate.model;

import com.johnny.validate.annotation.ListNotHasNull;
import com.johnny.validate.model.view.PersonAddView;
import com.johnny.validate.model.view.PersonModifyView;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Person {
    private long id;
    /**
     * 添加groups 属性，说明只在特定的验证规则里面起作用，不加则表示在使用Default规则时起作用
     */
    @NotNull(groups = {PersonAddView.class, PersonModifyView.class}, message = "添加、修改用户时名字不能为空")
    @ListNotHasNull.List({
            @ListNotHasNull(groups = {PersonAddView.class}, message = "添加上Name不能为空"),
            @ListNotHasNull(groups = {PersonModifyView.class}, message = "修改时Name不能为空")})
    private String name;

    @NotNull(groups = {PersonAddView.class}, message = "添加用户时地址不能为空")
    private String address;

    @Min(value = 18, groups = {PersonAddView.class}, message = "姓名不能低于18岁")
    @Max(value = 30, groups = {PersonModifyView.class}, message = "姓名不能超过30岁")
    private int age;
}