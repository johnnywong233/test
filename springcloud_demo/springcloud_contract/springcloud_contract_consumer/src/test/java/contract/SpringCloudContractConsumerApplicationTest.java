package contract;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * Author: Johnny
 * Date: 2017/11/21
 * Time: 11:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringCloudContractConsumerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureStubRunner(ids = {"com.johnny:springcloud_contract_provider:+:8080"}, workOffline = true)
@AutoConfigureStubRunner(ids = {"com.johnny:springcloud_contract_provider:+:8080"})
public class SpringCloudContractConsumerApplicationTest {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testGetCustomers() {
        ParameterizedTypeReference<Page> ptf = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<Page> responseEntity = restTemplate.exchange("http://localhost:8080/api/customers", HttpMethod.GET, null, ptf);
        Assert.assertEquals("size error!", 2, Objects.requireNonNull(responseEntity.getBody()).getData().size());
    }
}