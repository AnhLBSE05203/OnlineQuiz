package com.fpt.OnlineQuiz.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fpt.OnlineQuiz.model.Image;
import com.fpt.OnlineQuiz.service.ImageService;
import com.fpt.OnlineQuiz.service.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private ImageService imageService;
    //todo:
    // 1. process edit/add for other fields (id, name, etc.) & create proxy Image object
    //    on other controller. e.g: admin/subject/edit or admin/subject/add
    //    then forward to here (return forward:/imageMultipartFile)
    // 2. provide more @param on previous controller (request.addAttribute("...","..."))
    //       a. return link to redirect after file upload. e.g: "redirect:/admin/subject"
    //       b. created Image Id
    // 3. upload file here, get Image from DB through Id, add link to Image's imgSrc

    /**
     * Upload file (image) to AWS & update image source into DB after processing add/edit on other controller
     * Then redirect User to provided return link after uploading file
     *
     * @param file       file chosen to be uploaded
     * @param returnLink link to be redirected to after uploading, provided on previous controller
     * @param imgId      Image object's ID to process saving imgSrc to DB, provided on previous controller
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadImage")
    public String uploadMultipartFile(@RequestParam("file") MultipartFile[] file, @RequestParam("returnLink") String returnLink, @RequestParam("imgId") int imgId) throws IOException {
        List<String> listImage = new ArrayList<>();
        if (file != null && file.length != 0) {
            // upload image
            listImage = this.uploadImageService.saveFileToS3(file);
            // update img src
            Image image = imageService.getById(imgId);
            image.setSrc(listImage.get(0));
        } else {
            listImage.add("");
        }
        return returnLink;
    }

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

