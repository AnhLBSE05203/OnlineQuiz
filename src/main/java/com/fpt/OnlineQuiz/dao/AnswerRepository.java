package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.model.Answer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.SQLException;
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
    public List<Answer> getAnswersByQuestionId(int question_id){
        try {
            //TODO fix SQL
            StringBuilder sb = new StringBuilder();
            sb.append("select a from answer a where a.question.id =:id");
            Query query = em.createQuery(sb.toString(), Answer.class);
            query.setParameter("id", question_id);
            return (List<Answer>) query.getResultList();
        }catch (Exception exception){
            return null;
        }
    }
}