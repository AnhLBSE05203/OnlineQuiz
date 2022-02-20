package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.model.Course;
import com.fpt.OnlineQuiz.model.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public class QuestionRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Question> getQuestionsBySubjectId(int subject_id){
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select q from Question q where q.subject.id =:id");
            String sql = sb.toString();
            Query query = em.createQuery(sql, Question.class);
            query.setParameter("id", subject_id);
            query.setMaxResults(3);
            return (List<Question>) query.getResultList();
        }catch (NoResultException e){
            return null;
        }
    }

    public void addQuestion(Question question){
        em.persist(question);
    }

    public void updateQuestion(Question question){
        em.merge(question);
        em.flush();
    }
    public Question getQuestionByQuestionId(int question_id){
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select q from Question q where question_id =:id");
            String sql = sb.toString();
            Query query = em.createQuery(sql, Question.class);
            query.setParameter("id", question_id);
            query.setMaxResults(3);
            return (Question) query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
}