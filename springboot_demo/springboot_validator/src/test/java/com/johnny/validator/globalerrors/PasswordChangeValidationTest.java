package com.johnny.validator.globalerrors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//import org.springframework.boot.test.SpringApplicationConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
@SpringBootTest
@WebAppConfiguration
public class PasswordChangeValidationTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void failsWhenEmptyPasswordsGiven() throws Exception {
        this.mockMvc.perform(post("/globalerrors/password")
                .param("password", "").param("confirmedPassword", ""))
                .andExpect(model().attributeHasFieldErrors("passwordForm", "password", "confirmedPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("globalerrors/password"));
    }

    @Test
    public void failsWhenDifferentPasswordsGiven() throws Exception {
        this.mockMvc.perform(post("/globalerrors/password")
                .param("password", "test").param("confirmedPassword", "other"))
                .andExpect(model().hasErrors())
                .andExpect(status().isOk())
                .andExpect(view().name("globalerrors/password"));
    }

    @Test
    public void failsWithGlobalErrorWhenDifferentPasswordsGiven() throws Exception {
        this.mockMvc.perform(post("/globalerrors/password")
                .param("password", "test").param("confirmedPassword", "other"))
                .andExpect(GlobalErrorsMatchers.globalErrors().hasGlobalError("passwordForm", "passwords do not match"))
                .andExpect(status().isOk())
                .andExpect(view().name("globalerrors/password"));
    }

    @Test
    public void passesWhenSamePasswordsGiven() throws Exception {
        this.mockMvc.perform(post("/globalerrors/password")
                .param("password", "test").param("confirmedPassword", "test"))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:password"));
    }
}