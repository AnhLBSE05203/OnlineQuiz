package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.ImageRepository;
import com.fpt.OnlineQuiz.model.Image;
import com.fpt.OnlineQuiz.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image getById(int id) {
        return imageRepository.getById(id);
    }

    @Override
    public void addImage(Image image) {
        imageRepository.addImage(image);
    }

    @Override
    public void updateImage(Image image) {
        imageRepository.updateImage(image);
    }
}
