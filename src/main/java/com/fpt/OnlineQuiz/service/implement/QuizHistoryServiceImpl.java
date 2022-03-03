package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.model.Question;
import com.fpt.OnlineQuiz.model.QuizHistory;
import com.fpt.OnlineQuiz.service.QuizHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizHistoryServiceImpl implements QuizHistoryService {
    @Override
    public List<QuizHistory> finByAccountId(int id) {
        return null;
    }

    @Override
    public List<Question> questionListByPackage(int id) {
        return null;
    }

    @Override
    public Iterable<QuizHistory> getAllQuiz() {
        return null;
    }
}
