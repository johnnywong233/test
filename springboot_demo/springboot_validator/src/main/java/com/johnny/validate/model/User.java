package com.johnny.validate.model;

import com.johnny.validate.annotation.ListNotHasNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class User {
    private int id;

    @NotNull(message = "名字不能为空")
    private String name;

    @Length(max = 50, min = 10, message = "地址必须在10~20个字符之间")
    private String address;

    @Min(value = 18, message = "年龄必须 > 18 岁")
    private int age;

    /**
     * 所拥有的书籍列表
     */
    @NotEmpty(message = "所拥有书籍不能为空")
    @ListNotHasNull(message = "List 中不能含有null元素")
    @Valid
    private List<Book> books;
}