package demo1.test;

import demo1.entity.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Created by Johnny on 2018/2/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LdapTest {
    @Resource
    private PersonRepository personRepository;

    @Test
    public void findAll() {
        personRepository.findAll().forEach(System.out::println);
    }
}
