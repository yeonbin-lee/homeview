package com.example.demo1.controller;

import com.example.demo1.service.S3UploadService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/s3")
@AllArgsConstructor
@RestController
public class S3Controller {
    private S3UploadService s3UploadService;
    @PostMapping("/upload")
    public String upload(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        String fileURL = s3UploadService.saveFile(multipartFile);
        return fileURL;
    }

    @GetMapping("/delete")
    public void delete(String fileName){
        s3UploadService.deleteImage(fileName);
    }

}


