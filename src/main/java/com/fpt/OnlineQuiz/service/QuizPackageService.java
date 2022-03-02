package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.Quiz;
import com.fpt.OnlineQuiz.model.QuizPackage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizPackageService {
    List<QuizPackage> finByAccountId(int id);
    List<Quiz> quizListByPackage(int id);
    Iterable<QuizPackage> getAllQuiz();

}
