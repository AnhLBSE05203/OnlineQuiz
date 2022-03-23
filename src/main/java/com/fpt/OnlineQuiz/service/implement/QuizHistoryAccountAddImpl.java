package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.CRUDRepository.CRUDQuizHistoryAccountAddRepository;
import com.fpt.OnlineQuiz.model.QuizHistoryAccountAdd;
import com.fpt.OnlineQuiz.service.QuizHistoryAccountAddServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizHistoryAccountAddImpl implements QuizHistoryAccountAddServices{

    @Autowired
    CRUDQuizHistoryAccountAddRepository crudQuizHistoryAccountAddRepository;

    @Override
    public QuizHistoryAccountAdd addOwnerOrAdd(QuizHistoryAccountAdd add) {
        return crudQuizHistoryAccountAddRepository.save(add);
    }

    @Override
    public QuizHistoryAccountAdd findByAccountAndHisId(int accountId, int hisId) {
        return crudQuizHistoryAccountAddRepository.findByAccountAndHisId(accountId,hisId);
    }

    @Override
    public void delete(QuizHistoryAccountAdd quizHistoryAccountAdd) {
        crudQuizHistoryAccountAddRepository.delete(quizHistoryAccountAdd);
    }


}
