package com.johnny.validator.groupsequence;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenRetrievalService {

    String getToken(String code) throws TokenNotFoundException {
        if ("42".equals(code)) { // possible NPE
            throw new TokenNotFoundException();
        }
        return UUID.randomUUID().toString();
    }
}

class TokenNotFoundException extends Exception {
}
