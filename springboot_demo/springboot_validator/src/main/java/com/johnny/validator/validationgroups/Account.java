package com.johnny.validator.validationgroups;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@SamePasswords(groups = {Account.ValidationStepTwo.class})
public class Account implements PasswordAware {

    @NotBlank(groups = {ValidationStepOne.class})
    private String username;
    @Email(groups = {ValidationStepOne.class})
    @NotBlank(groups = {ValidationStepOne.class})
    private String email;
    @NotBlank(groups = {ValidationStepTwo.class})
    @StrongPassword(groups = {ValidationStepTwo.class})
    private String password;
    @NotBlank(groups = {ValidationStepTwo.class})
    private String confirmedPassword;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    interface ValidationStepOne {
        // validation group marker interface
    }

    interface ValidationStepTwo {
        // validation group marker interface
    }
}