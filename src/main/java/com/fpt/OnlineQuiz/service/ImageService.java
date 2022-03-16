package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.Image;

public interface ImageService {

    public Image getById(int id);

    public void addImage(Image image);

    public void updateImage(Image image);
}
