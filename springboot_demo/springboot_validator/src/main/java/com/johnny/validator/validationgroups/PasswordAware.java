package com.johnny.validator.validationgroups;

interface PasswordAware {
    String getPassword();

    String getConfirmedPassword();
}