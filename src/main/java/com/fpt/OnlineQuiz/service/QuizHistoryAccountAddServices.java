package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.QuizHistory;
import com.fpt.OnlineQuiz.model.QuizHistoryAccountAdd;
import org.springframework.stereotype.Service;

public interface QuizHistoryAccountAddServices {
    QuizHistoryAccountAdd addOwnerOrAdd(QuizHistoryAccountAdd add);
    QuizHistoryAccountAdd findByAccountAndHisId(int accountId,int hisId);
    void delete (QuizHistoryAccountAdd quizHistoryAccountAdd);

}
