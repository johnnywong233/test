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

@WebAppConfiguration
public class RootInventoryControllerInIntegrationTests {

    @Resource
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testList() throws Exception {
        this.mockMvc.perform(get("/root_inventories/")).andExpect(status().isOk());
        this.mockMvc.perform(get("/b1/root_inventories/").servletPath("/b1")).andExpect(status().isNotFound());
    }

    @Test
    public void testTenantList() throws Exception {
        this.mockMvc.perform(get("/b2/root_inventories/tenant").servletPath("/b2")).andExpect(status().isOk());
        this.mockMvc.perform(get("/root_inventories/tenant")).andExpect(status().isNotFound());
    }
}
