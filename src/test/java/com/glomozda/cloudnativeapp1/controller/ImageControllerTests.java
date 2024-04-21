package com.glomozda.cloudnativeapp1.controller;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Tags({@Tag("IntegrationTest"), @Tag("ControllerTest"), @Tag("ImageControllerTest")})
@AutoConfigureMockMvc
class ImageControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetImageById_OK() throws Exception {
        //Act and Assert
        mockMvc.perform(get("/image/Blue")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void testPostImage_OK() throws Exception {
        //Arrange
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "TestImage.png",
                MediaType.IMAGE_PNG_VALUE,
                Files.readAllBytes(Path.of("src/test/resources/TestImage.png")));

        //Act and Assert
        mockMvc.perform(multipart("/image").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("Successful"));
    }
}