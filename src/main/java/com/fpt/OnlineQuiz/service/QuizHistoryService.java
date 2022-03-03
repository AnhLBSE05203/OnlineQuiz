package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.Question;
import com.fpt.OnlineQuiz.model.Quiz;
import com.fpt.OnlineQuiz.model.QuizHistory;
import com.fpt.OnlineQuiz.model.QuizPackage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizHistoryService {
    List<QuizHistory> finByAccountId(int id);
    List<Question> questionListByPackage(int id);
    Iterable<QuizHistory> getAllQuiz();

}
