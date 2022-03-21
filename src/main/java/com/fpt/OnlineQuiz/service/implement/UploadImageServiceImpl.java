package com.fpt.OnlineQuiz.service.implement;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fpt.OnlineQuiz.service.UploadImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UploadImageServiceImpl implements UploadImageService {

    @Autowired
    private AmazonS3 s3client;

    private static final Logger logger = LogManager.getLogger(UploadImageServiceImpl.class);

    @Override
    public List<String> saveFileToS3(MultipartFile[] file) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = simpleDateFormat.format(new Date());

        List<String> listImage = new ArrayList<>();
        if (file != null && file.length != 0) {
            for (MultipartFile imageValue : file) {
                try {
                    String fileName = date + imageValue.getOriginalFilename();
                    String path = System.getProperty("user.dir") + fileName;
                    path = URLDecoder.decode(path, "UTF-8");
                    File fileOut = new File(path);
                    FileOutputStream fos = new FileOutputStream(fileOut);
                    fos.write(imageValue.getBytes());
                    fos.close();
                    this.s3client.putObject(new PutObjectRequest("auctionimage", "image/" + fileName, fileOut)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
                    fileOut.deleteOnExit();
                    listImage.add("https://auctionimage.s3.ap-southeast-1.amazonaws.com/image/" + fileName);
                } catch (AmazonServiceException e) {
                    logger.error(e.getMessage(), e);
                    listImage.add("");
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    listImage.add("");
                }
            }
        } else {
            listImage.add("");
        }
        return listImage;
    }

    @Override
    public List<String> saveFileToS3(MultipartFile file) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = simpleDateFormat.format(new Date());

        List<String> listImage = new ArrayList<>();
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = date + file.getOriginalFilename();
                String path = System.getProperty("user.dir") + fileName;
                path = URLDecoder.decode(path, "UTF-8");
                File fileOut = new File(path);
                FileOutputStream fos = new FileOutputStream(fileOut);
                fos.write(file.getBytes());
                fos.close();
                this.s3client.putObject(new PutObjectRequest("auctionimage", "image/" + fileName, fileOut)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                fileOut.deleteOnExit();
                listImage.add("https://auctionimage.s3.ap-southeast-1.amazonaws.com/image/" + fileName);
            } catch (AmazonServiceException e) {
                logger.error(e.getMessage(), e);
                listImage.add("");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                listImage.add("");
            }
        } else {
            listImage.add("");
        }
        return listImage;
    }
}
