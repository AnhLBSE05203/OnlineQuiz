package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.QuizHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizHistoryService {
      List<QuizHistory> getQuizByAccountAdd(int id);
      List<QuizHistory> listQuizHistory(int historyAccountId);
      QuizHistory addQuizPackage(QuizHistory quiz);
      QuizHistory findId(int id);
      List<QuizHistory> finAllAccountId();
}
