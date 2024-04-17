package com.glomozda.cloudnativeapp1.controller;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Tags({@Tag("ControllerTest"), @Tag("AnonymousControllerTest"), @Tag("HomeControllerTest")})
@AutoConfigureMockMvc
class HomeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    void testHome_Anonymous_302_RedirectToHome() throws Exception {
        //Act and Assert
        mockMvc.perform(get("/")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}