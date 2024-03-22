package contract;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import provider.SingerRepository;
import provider.SingerRestController;

import java.util.Arrays;

/**
 * Author: Johnny
 * Date: 2017/11/21
 * Time: 11:36
 */
@SpringBootTest
public class SpringCloudContractApplicationTest {

    @Resource
    private SingerRestController singerRestController;
    @MockBean
    private SingerRepository singerRepository;

    @BeforeEach
    public void before() {
        Mockito.when(singerRepository.findAll()).thenReturn(
                Arrays.asList(new Singer(1L, "sam"), new Singer(2L, "andy")));
        RestAssuredMockMvc.standaloneSetup(this.singerRepository);
    }

    @Test
    public void test() {
        singerRestController.getCustomers();
    }

}