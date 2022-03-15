package com.fpt.OnlineQuiz.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadImageService {
    List<String> saveFileToS3(MultipartFile[] file);

    public List<String> saveFileToS3(MultipartFile file);
}
