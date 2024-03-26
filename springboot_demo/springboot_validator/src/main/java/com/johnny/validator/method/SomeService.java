package com.johnny.validator.method;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
public class SomeService {

    @Length(min = 3, max = 5)
    public void createUser(@NotBlank @Email String email,
                           @NotBlank String username,
                           @NotBlank String password) {
        log.info("email:{},username:{},password:{}", email, username, password);
    }

}
