package com.glomozda.cloudnativeapp1.service;

import com.glomozda.cloudnativeapp1.client.Clients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

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
                .putObject(s3BucketName, id + "/" + image.getOriginalFilename(),
                        new ByteArrayInputStream(image.getBytes()), null);
        return id;
    }

    @Override
    public byte[] getImageById(String id) throws IOException {
        return Clients.getInstance().getS3Client().getObject(s3BucketName,id).getObjectContent().readAllBytes();
    }
}
