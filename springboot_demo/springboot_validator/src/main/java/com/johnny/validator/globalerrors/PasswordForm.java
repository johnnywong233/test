package com.johnny.validator.globalerrors;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@SamePasswords
@Data
public class PasswordForm {

    @NotBlank
    private String password;

    @NotBlank
    private String confirmedPassword;
}
