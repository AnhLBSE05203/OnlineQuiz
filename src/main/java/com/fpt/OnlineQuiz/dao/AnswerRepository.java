package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.model.Answer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AnswerRepository {

    @PersistenceContext
    private EntityManager em;

    public void addAnswers(List<Answer> answerList){
        for (Answer answer : answerList){
            em.persist(answer);
        }
    }
}
