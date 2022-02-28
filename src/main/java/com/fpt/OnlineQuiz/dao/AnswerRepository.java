package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.model.Answer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AnswerRepository {

    @PersistenceContext
    private EntityManager em;

    public void addAnswers(List<Answer> answerList) {
        for (Answer answer : answerList) {
            em.persist(answer);
        }
    }

    public List<Answer> getAnswersByQuestionId(int questionId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select a from Answer a where question_id =:id");
            Query query = em.createQuery(sb.toString(), Answer.class);
            query.setParameter("id", questionId);
            return (List<Answer>) query.getResultList();
        } catch (Exception exception) {
            return null;
        }
    }

    public void updateAnswers(List<Answer> answers) {

        for (Answer a : answers) {
            em.merge(a);
        }
        em.flush();
    }

    public void deleteAnswersByQuestionId(int questionId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from Answer where question_id=:id");
            Query query = em.createQuery(sb.toString());
            query.setParameter("id", questionId);
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
