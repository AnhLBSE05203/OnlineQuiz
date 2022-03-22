package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.ScreenRepository;
import com.fpt.OnlineQuiz.model.Screen;
import com.fpt.OnlineQuiz.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenServiceImpl implements ScreenService {
    @Autowired
    private ScreenRepository screenRepository;

    @Override
    public List<Screen> findAll() {
        return screenRepository.findAll();
    }
}
