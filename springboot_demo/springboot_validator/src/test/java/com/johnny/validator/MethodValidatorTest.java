package com.johnny.validator;

import com.johnny.validate.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MethodValidatorTest {

	@Resource
	private PersonService personService;
	@Test
	public void testMethodValidator() {
		personService.createPerson(null,18);
	}
}