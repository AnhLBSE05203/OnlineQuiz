package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.CRUDRepository.CRUDQuizHistoryAccountAddRepository;
import com.fpt.OnlineQuiz.model.QuizHistoryAccountAdd;
import com.fpt.OnlineQuiz.service.QuizHistoryAccountAddServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizHistoryAccountAddImpl implements QuizHistoryAccountAddServices {

    @Autowired
    CRUDQuizHistoryAccountAddRepository crudQuizHistoryAccountAddRepository;

    @Override
    public QuizHistoryAccountAdd addOwnerOrAdd(QuizHistoryAccountAdd add) {
        return crudQuizHistoryAccountAddRepository.save(add);
    }
}
