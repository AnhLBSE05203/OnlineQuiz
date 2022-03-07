package com.fpt.OnlineQuiz.controller;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fpt.OnlineQuiz.service.UploadImageService;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class UploadImageController {

    @Autowired
    private AmazonS3 s3client;

    @Autowired
    private UploadImageService uploadImageService;

    @PostMapping("/imageMultipartFile")
    public Map<String, String> uploadMultipartFile(@RequestParam("file") MultipartFile[] file) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Map<String, String> values = new HashMap<String, String>();
        String date = simpleDateFormat.format(new Date());
        if (file != null && file.length != 0) {
            for (MultipartFile imageValue : file) {
                try {
                    String fileName = date + imageValue.getOriginalFilename();
                    File fileOut = new File(
                            this.getClass().getClassLoader().getResource(".").getFile() + date + fileName);
                    FileOutputStream fos = new FileOutputStream(fileOut);
                    fos.write(imageValue.getBytes());
                    fos.close();
                    this.s3client.putObject(new PutObjectRequest("auctionimage", "image/" + fileName, fileOut)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
                    values.put("imageUrl", "https://auctionimage.s3.ap-southeast-1.amazonaws.com/image/" + fileName);
                    return values;
                } catch (Exception e) {
                    values.put("imageUrl", "");
                    return values;
                }
            }
        }
        values.put("imageUrl", "");
        return values;
    }

    @PostMapping("/imageMultipartFiles")
    public ResponseEntity<?> uploadMultipartFiles(@RequestParam("file") MultipartFile[] file) throws IOException {
        List<String> listImage = new ArrayList<>();
        if (file != null && file.length != 0) {
            listImage = this.uploadImageService.saveFileToS3(file);
        } else {
            listImage.add("");
        }
        return ResponseEntity.ok(listImage);
    }
}
