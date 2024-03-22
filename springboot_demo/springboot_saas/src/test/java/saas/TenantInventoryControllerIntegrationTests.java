package saas;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by johnny on 14/11/11.
 */
@WebAppConfiguration
public class TenantInventoryControllerIntegrationTests {

    @Resource
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testList() throws Exception {
        this.mockMvc.perform(get("/b1/tenant_inventories/").servletPath("/b1")).andExpect(status().isOk());
        this.mockMvc.perform(get("/tenant_inventories/")).andExpect(status().isNotFound());
    }

    @Test
    public void estRootList() throws Exception {
        this.mockMvc.perform(get("/tenant_inventories/root")).andExpect(status().isOk());
        this.mockMvc.perform(get("/b1/tenant_inventories/root").servletPath("/b1")).andExpect(status().isNotFound());
    }
}
