package com.glomozda.cloudnativeapp1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glomozda.cloudnativeapp1.domain.MockEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(path="/image")
public class ImageController {
    @Value("${environment.s3-bucket-name}")
    private String s3BucketName;

    @Value("${environment.dynamodb-table-name}")
    private String dynamoDbTableName;

    @PostMapping("")
    public ResponseEntity<String> postImage(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();

        log.info("File Name: " + file.getOriginalFilename());
        log.info("File Content Type: " + file.getContentType());
        log.info("File Content Size: " + bytes.length);

        return (new ResponseEntity<>("Successful", null, HttpStatus.OK));
    }

    @GetMapping("/{id}")
    public String getImageById(@PathVariable String id) throws JsonProcessingException {
        MockEntity mockEntity = new MockEntity(Long.valueOf(id), "Mock One");
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(mockEntity);
    }

}
