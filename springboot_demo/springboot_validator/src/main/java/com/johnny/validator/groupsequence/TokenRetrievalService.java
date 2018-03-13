package com.johnny.validator.groupsequence;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenRetrievalService {

    String getToken(String code) throws TokenNotFoundException {
        if (code.equals("42")) { // possible NPE
            throw new TokenNotFoundException();
        }
        return UUID.randomUUID().toString();
    }
}

class TokenNotFoundException extends Exception {
}
