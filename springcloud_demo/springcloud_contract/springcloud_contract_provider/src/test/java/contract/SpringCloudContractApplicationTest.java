package contract;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import provider.SingerRepository;
import provider.SingerRestController;

import java.util.Arrays;

/**
 * Author: Johnny
 * Date: 2017/11/21
 * Time: 11:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringCloudContractApplicationTest {

    @Autowired
    private SingerRestController singerRestController;
    @MockBean
    private SingerRepository singerRepository;

    @Before
    public void before() {
        Mockito.when(singerRepository.findAll()).thenReturn(
                Arrays.asList(new Singer(1L, "sam"), new Singer(2L, "andy")));
        RestAssuredMockMvc.standaloneSetup(this.singerRepository);
    }

}