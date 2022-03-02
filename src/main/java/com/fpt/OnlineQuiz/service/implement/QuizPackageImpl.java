package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.CRUDRepository.CRUDQuizPackage;
import com.fpt.OnlineQuiz.model.Quiz;
import com.fpt.OnlineQuiz.model.QuizPackage;
import com.fpt.OnlineQuiz.service.QuizPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizPackageImpl implements QuizPackageService {

    @Autowired
    CRUDQuizPackage quizPackage;

    @Override
    public List<QuizPackage> finByAccountId(int id) {
        return quizPackage.findByAccountId(id);
    }

    @Override
    public List<Quiz> quizListByPackage(int id) {
        return quizPackage.listQuizByPackage(id);
    }

    @Override
    public Iterable<QuizPackage> getAllQuiz() {
        return quizPackage.findAll();
    }


}
