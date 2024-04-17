package com.glomozda.cloudnativeapp1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glomozda.cloudnativeapp1.domain.MockEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/")
public class HomeController {
    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("")
    public String home() throws JsonProcessingException {
        MockEntity mockEntity = new MockEntity(1L, "Mock One");
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mockEntity);
    }

}
