package com.johnny.validate.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Slf4j
public class PersonService {
    public void createPerson(@NotNull String name, @Min(18) int age) {
        log.info(name + age);
    }
}