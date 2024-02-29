package com.johnny.validator.globalerrors;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@SamePasswords
@Data
public class PasswordForm {

    @NotBlank
    private String password;

    @NotBlank
    private String confirmedPassword;
}
