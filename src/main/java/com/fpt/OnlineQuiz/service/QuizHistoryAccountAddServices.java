package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.QuizHistory;
import com.fpt.OnlineQuiz.model.QuizHistoryAccountAdd;
import org.springframework.stereotype.Service;

@Service
public interface QuizHistoryAccountAddServices {
    QuizHistoryAccountAdd addOwnerOrAdd(QuizHistoryAccountAdd add);
}
