package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.QuizHistory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface QuizHistoryService {
      List<QuizHistory> getQuizByAccountAdd(int id);
      List<QuizHistory> listQuizHistory(int historyAccountId);
      QuizHistory addQuizPackage(QuizHistory quiz);
      QuizHistory findId(int id);
      List<QuizHistory> finAllAccountId(String name);
      QuizHistory checkExist(int historyId,int accountId);
      Optional<QuizHistory> getByQHId(int historyId);

}
