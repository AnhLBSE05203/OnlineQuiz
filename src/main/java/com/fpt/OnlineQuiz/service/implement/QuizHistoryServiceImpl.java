package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.CRUDRepository.CRUDQuizHistory;
import com.fpt.OnlineQuiz.model.QuizHistory;
import com.fpt.OnlineQuiz.service.QuizHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizHistoryServiceImpl implements QuizHistoryService {

    @Autowired
    CRUDQuizHistory quizHistory;

    @Override
    public List<QuizHistory> getQuizByAccountAdd(int id) {
        return quizHistory.listQuizByAccountAdd(id);
    }

    @Override
    public List<Object[]> listQuizHistory(int historyAccountId) {
        return quizHistory.listHistoryQuiz(historyAccountId);
    }
}
