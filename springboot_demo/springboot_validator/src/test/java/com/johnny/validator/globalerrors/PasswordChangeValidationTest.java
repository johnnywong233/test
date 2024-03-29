package com.johnny.validator.globalerrors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@WebAppConfiguration
public class PasswordChangeValidationTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
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