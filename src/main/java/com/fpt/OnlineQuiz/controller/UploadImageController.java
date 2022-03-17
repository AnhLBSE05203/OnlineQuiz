package com.fpt.OnlineQuiz.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fpt.OnlineQuiz.model.Image;
import com.fpt.OnlineQuiz.service.ImageService;
import com.fpt.OnlineQuiz.service.UploadImageService;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(Constants.LINK_IMAGE_CONTROLLER)
public class UploadImageController {

    @Autowired
    private AmazonS3 s3client;

    @Autowired
    private UploadImageService uploadImageService;

    @Autowired
    private ImageService imageService;

    /**
     * Upload file (image) to AWS & update image source into DB after processing add/edit on other controller
     * Then redirect User to provided return link after uploading file
     * request attributes needed 'returnLink' & 'imgId'
     *
     * @param file    file chosen to be uploaded
     * @param request HttpServletRequest
     * @return
     * @throws IOException
     */
    @PostMapping(Constants.LINK_IMAGE_CONTROLLER_UPLOAD_IMAGE)
    public String uploadMultipartFile(@RequestParam(Constants.REQUEST_PARAM_FILE) MultipartFile file, HttpServletRequest request) throws IOException {

        String returnLink = (String) request.getAttribute(Constants.REQUEST_ATTRIBUTE_RETURN_LINK);
        int imgId = (int) request.getAttribute(Constants.REQUEST_ATTRIBUTE_IMG_ID);
        List<String> listImage = new ArrayList<>();
        if (file != null && !file.isEmpty()) {
            // upload image
            listImage = this.uploadImageService.saveFileToS3(file);
            // update img src
            Image image = imageService.getById(imgId);
            image.setSrc(listImage.get(0));
            imageService.updateImage(image);
        } else {
            listImage.add("");
        }
        return returnLink;
    }

    @PostMapping(Constants.LINK_IMAGE_CONTROLLER_UPLOAD_MULTIPART_FILE)
    public Map<String, String> uploadMultipartFile(@RequestParam(Constants.REQUEST_PARAM_FILE) MultipartFile[] file) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Map<String, String> values = new HashMap<String, String>();
        String date = simpleDateFormat.format(new Date());
        if (file != null && file.length != 0) {
            for (MultipartFile imageValue : file) {
                try {
                    String fileName = date + imageValue.getOriginalFilename();
                    String path = this.getClass().getClassLoader().getResource(".").getFile() + fileName;
                    path = URLDecoder.decode(path, "UTF-8");
                    File fileOut = new File(path);
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

    @PostMapping(Constants.LINK_IMAGE_CONTROLLER_UPLOAD_MULTIPART_FILES)
    public ResponseEntity<?> uploadMultipartFiles(@RequestParam(Constants.REQUEST_PARAM_FILE) MultipartFile[] file) throws IOException {
        List<String> listImage = new ArrayList<>();
        if (file != null && file.length != 0) {
            listImage = this.uploadImageService.saveFileToS3(file);
        } else {
            listImage.add("");
        }
        return ResponseEntity.ok(listImage);
    }
}

