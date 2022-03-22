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
    public List<QuizHistory> listQuizHistory(int historyAccountId) {
        return quizHistory.listHistoryQuiz(historyAccountId);
    }

    @Override
    public QuizHistory addQuizPackage(QuizHistory quiz) {
        return quizHistory.save(quiz);
    }

    @Override
    public QuizHistory findId(int id) {
        return quizHistory.findForAdd(id);
    }

    @Override
    public List<QuizHistory> finAllAccountId() {
        return quizHistory.allIfAccountNotNull();
    }


}
