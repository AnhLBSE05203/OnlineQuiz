package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.model.QuizPackage;
import com.fpt.OnlineQuiz.service.QuizPackageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizPackageImpl implements QuizPackageService {
    @Override
    public List<QuizPackage> finByAccountId(int id) {
        return finByAccountId(id);
    }
}
