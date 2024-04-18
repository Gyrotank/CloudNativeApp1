package com.glomozda.cloudnativeapp1.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ImageService {
    String uploadImage(MultipartFile image) throws IOException;

    byte[] getImageById(String id) throws IOException;
}
