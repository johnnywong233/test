package com.johnny.validator;

import com.johnny.validate.service.PersonService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MethodValidatorTest {

    @Resource
    private PersonService personService;

    @Test
    public void testMethodValidator() {
        personService.createPerson(null, 18);
    }
}