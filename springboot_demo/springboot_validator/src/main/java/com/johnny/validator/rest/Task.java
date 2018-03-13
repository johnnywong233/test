package com.johnny.validator.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Task {
    @NotBlank(message = "Task name must not be blank!")
    private String name;

    @NotBlank(message = "Task description must not be blank!")
    private String description;
}