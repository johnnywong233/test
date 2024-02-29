package com.johnny.validator.method;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Service
@Validated
public class SomeService {

    @Length(min = 3, max = 5)
    public String createUser(@NotBlank @Email String email,
                      @NotBlank String username,
                      @NotBlank String password) {
        return username;
    }

}
