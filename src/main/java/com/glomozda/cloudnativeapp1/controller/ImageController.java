package com.glomozda.cloudnativeapp1.controller;

import com.glomozda.cloudnativeapp1.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(path="/image")
public class ImageController {
    protected final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("")
    public ResponseEntity<String> postImage(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();

        log.info("File Name: " + file.getOriginalFilename());
        log.info("File Content Type: " + file.getContentType());
        log.info("File Content Size: " + bytes.length);

        String uploadedFileId = imageService.uploadImage(file);

        return (new ResponseEntity<>(uploadedFileId, null, HttpStatus.OK));
    }

    @GetMapping("/{label}")
    public ResponseEntity<String> getImagesListByLabel(@PathVariable String label) {
        return (new ResponseEntity<>(imageService.getImagesDataByLabel(label), null, HttpStatus.OK));
    }
}
