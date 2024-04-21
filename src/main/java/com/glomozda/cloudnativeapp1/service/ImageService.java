package com.glomozda.cloudnativeapp1.service;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ImageService {
    HttpStatusCode uploadImage(String id, MultipartFile imageFile) throws IOException;

    String getImagesDataByLabel(String label);
}
