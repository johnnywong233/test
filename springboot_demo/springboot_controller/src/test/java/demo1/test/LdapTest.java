package demo1.test;

import demo1.entity.PersonRepository;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by Johnny on 2018/2/3.
 */
@SpringBootTest
public class LdapTest {
    @Resource
    private PersonRepository personRepository;

    @Test
    public void findAll() {
        personRepository.findAll().forEach(System.out::println);
    }
}
