package com.glomozda.cloudnativeapp1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path="/")
public class HomeController {
    @Value("${environment.s3-bucket-name}")
    private String s3BucketName;

    @Value("${environment.dynamodb-table-name}")
    private String dynamoDbTableName;

    @GetMapping("")
    public ResponseEntity<String> home() {
        log.info("Bucket: " + s3BucketName);
        log.info("DynamoDB Table: " + dynamoDbTableName);

        return (new ResponseEntity<>("Bucket: " + s3BucketName + "\nDynamoDB Table: " + dynamoDbTableName,
                null, HttpStatus.OK));
    }
}
