package com.glomozda.cloudnativeapp1.client;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Clients {
    private final AmazonDynamoDB dynamoDbClient;
    private final AmazonS3 s3Client;

    private static Clients instance = null;

    private Clients() {
        dynamoDbClient = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(Regions.EU_NORTH_1)
                .build();

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(Regions.EU_NORTH_1)
                .build();
    }

    public static Clients getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Clients();
        }
        return instance;
    }
}
