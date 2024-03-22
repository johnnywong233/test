package com.johnny.validator.bettermessages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@WebAppConfiguration
public class CodeValidationControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void displaysABidForm() throws Exception {
        this.mockMvc.perform(get("/bettermessages/bid"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("bid", any(Bid.class)))
                .andExpect(view().name("bettermessages/bid"));
    }

    @Test
    public void postsAValidBid() throws Exception {
        this.mockMvc.perform(post("/bettermessages/bid")
                        .param("bidder", "John Smith")
                        .param("expiresAt", "2020-01-01")
                        .param("price", "11.88"))
                .andExpect(content().string(
                        not(containsString("Form contains errors. Please try again."))));
    }

    @Test
    public void postsABidWithBidderTooShort() throws Exception {
        this.mockMvc.perform(post("/bettermessages/bid").param("bidder", "John")) // too short
                .andExpect(content().string(allOf(
                        containsString("Form contains errors. Please try again."),
                        containsString("&quot;John&quot; is too short. Should not be shorter than 5"))));
    }

    @Test
    public void postsABidWithBidderWayTooShort() throws Exception {
        this.mockMvc.perform(post("/bettermessages/bid").param("bidder", "J")) // way too short
                .andExpect(content().string(
                        allOf(
                                containsString("Form contains errors. Please try again."),
                                containsString("&quot;J&quot; is way too short. Should not be shorter than 5"))));
    }

    @Test
    public void postsABidWithBidderTooLong() throws Exception {
        this.mockMvc.perform(post("/bettermessages/bid").param("bidder", "John S. Smith")) // too long
                .andExpect(content().string(
                        allOf(
                                containsString("Form contains errors. Please try again."),
                                containsString("&quot;John S. Smith&quot; is too long. Should not be longer than 10"))));
    }

    @Test
    public void postsABidWithBidderWayTooLong() throws Exception {
        this.mockMvc.perform(post("/bettermessages/bid").param("bidder", "John The Saint Smith"))
                .andExpect(content().string(
                        allOf(
                                containsString("Form contains errors. Please try again."),
                                containsString("&quot;John The Saint Smith&quot; is way too long. Should not be longer than 10"))));
    }

    @Test
    public void postsABidWithExpiresAtInPast() throws Exception {
        this.mockMvc.perform(post("/bettermessages/bid").param("expiresAt", "2010-01-01"))
                .andExpect(content().string(
                        allOf(
                                containsString("Form contains errors. Please try again."),
                                containsString("Value &quot;2010-01-01&quot; is not in future!"))));
    }

    @Test
    public void postsABidWithPriceLowerThanFive() throws Exception {
        this.mockMvc.perform(post("/bettermessages/bid").param("price", "4.99"))
                .andExpect(content().string(
                        allOf(
                                containsString("Form contains errors. Please try again."),
                                containsString("Value &quot;4.99&quot; is incorrect. Must be greater than or equal to 10.00"))));
    }
}