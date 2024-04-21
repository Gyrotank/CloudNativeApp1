package com.glomozda.cloudnativeapp1.service;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glomozda.cloudnativeapp1.client.Clients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final Clients clients;

    @Autowired
    public ImageServiceImpl(Clients clients) {
        this.clients = clients;
    }

    @Override
    public String uploadImage(MultipartFile imageFile) throws IOException {
        String id = UUID.randomUUID() + "-" + imageFile.getOriginalFilename();
        clients.getInstance().getS3Client()
                .putObject(s3BucketName, id,
                        new ByteArrayInputStream(imageFile.getBytes()), null);
        return id;
    }

    @Override
    public String getImagesDataByLabel(String label) {
        DynamoDB dynamoDB = new DynamoDB(clients.getInstance().getDynamoDbClient());
        Table table = dynamoDB.getTable(dynamoDbTableName);
        Iterator<Item> itemIterator = table.query("LabelValue", label).iterator();

        List<Map<String, Object>> items = new ArrayList<>();
        while (itemIterator.hasNext()) {
            items.add(itemIterator.next().asMap());
        }

        String result = "[]";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(items);
        } catch (JacksonException e) {
            log.error(e.getOriginalMessage());
        }
        return result;
    }
}
