package com.glomozda.cloudnativeapp1.service;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glomozda.cloudnativeapp1.client.Clients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    @Value("${environment.s3-bucket-name}")
    private String s3BucketName;

    @Value("${environment.dynamodb-table-name}")
    private String dynamoDbTableName;

    @Override
    public String uploadImage(MultipartFile image) throws IOException {
        String id = UUID.randomUUID().toString();
        Clients.getInstance().getS3Client()
                .putObject(s3BucketName, id,
                        new ByteArrayInputStream(image.getBytes()), null);
        return id;
    }

    @Override
    public String getImagesDataByLabel(String label) {
        DynamoDB dynamoDB = new DynamoDB(Clients.getInstance().getDynamoDbClient());
        Table table = dynamoDB.getTable(dynamoDbTableName);
        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression("#pk = :pkValue")
                .withNameMap(Map.of("#pk", "LabelValue"))
                .withValueMap(new ValueMap().withString(":pkValue", label));

        Iterator<Item> itemIterator = table.query(querySpec).iterator();

        List<Item> items = new ArrayList<>();
        while (itemIterator.hasNext()) {
            items.add(itemIterator.next());
        }

        String result = "[]";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            result = objectMapper.writeValueAsString(items);
        } catch (JacksonException e) {
            log.error(e.getOriginalMessage());
        }
        return result;
    }
}
