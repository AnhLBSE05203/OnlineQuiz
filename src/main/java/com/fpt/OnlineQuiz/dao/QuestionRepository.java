package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.dto.paging.Column;
import com.fpt.OnlineQuiz.dto.paging.Order;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Course;
import com.fpt.OnlineQuiz.model.Question;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
//            query.setMaxResults(3);
            return (Question) query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
    public List<Question> getByPagingRequest(PagingRequest pagingRequest) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select q from Question q where 1 = 1");
            // append filtering
            if (pagingRequest.getSearch() != null
                    && StringUtils.hasLength(pagingRequest.getSearch().getValue())) {
                String key = "'%" + pagingRequest.getSearch().getValue().toLowerCase() + "%'";
                sb.append(" AND lower(q.question) LIKE " + key);
                sb.append(" OR lower(q.answer) LIKE " + key);
            }
            // append sorting
            Order order = pagingRequest.getOrder().get(0);
            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns().get(columnIndex);
            sb.append(" ORDER BY "+ "q."  + column.getData() + " " + order.getDir());

            String sql = sb.toString();
            Query query = em.createQuery(sql, Question.class);
            query.setFirstResult(pagingRequest.getStart());
            query.setMaxResults(pagingRequest.getLength());
            return (List<Question>) query.getResultList();
        } catch (NoResultException exception) {
            return null;
        }
    }
    public long getQuestionCountByPagingRequest(PagingRequest pagingRequest) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT count(q) FROM Question q");
            // append filtering
            if (pagingRequest.getSearch() != null
                    && StringUtils.hasLength(pagingRequest.getSearch().getValue())) {
                String key = "'%" + pagingRequest.getSearch().getValue().toLowerCase() + "%'";
                sb.append(" AND lower(q.question) LIKE " + key);
                sb.append(" OR lower(q.answer) LIKE " + key);
            }

            String sql = sb.toString();
            Query query = em.createQuery(sql, Long.class);
            return (long) query.getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }
}