package com.johnny.validator.rest;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Task {
    @NotBlank(message = "Task name must not be blank!")
    private String name;

    @NotBlank(message = "Task description must not be blank!")
    private String description;
}