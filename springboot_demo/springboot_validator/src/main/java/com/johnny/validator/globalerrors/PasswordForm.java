package com.johnny.validator.globalerrors;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@SamePasswords
@Data
public class PasswordForm {

    @NotBlank
    private String password;

    @NotBlank
    private String confirmedPassword;
}
