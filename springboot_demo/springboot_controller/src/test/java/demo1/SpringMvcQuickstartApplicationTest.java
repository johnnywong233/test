package demo1;

import demo1.service.TestLogStarterService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Author: Johnny
 * Date: 2017/4/12
 * Time: 19:04
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class SpringMvcQuickstartApplicationTest extends TestCase {

    //    @Autowired
    @MockBean
    private TestLogStarterService testLogStarterService;

    @Test
    public void contextLoads() {
    }

    //TODO
    @Test
    public void testLog() {
        testLogStarterService.core(1111);
        testLogStarterService.test(1111);
        testLogStarterService.work(1111);
    }
}