package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.dto.QuestionDTO;
import com.fpt.OnlineQuiz.dto.paging.Column;
import com.fpt.OnlineQuiz.dto.paging.Order;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Question;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class QuestionRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Question> getQuestionsByLessonId(int lessonId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select q from Question q where q.lesson.id =:id");
            String sql = sb.toString();
            Query query = em.createQuery(sql, Question.class);
            query.setParameter("id", lessonId);
            query.setMaxResults(3);
            return (List<Question>) query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    public List<QuestionDTO> getQuestionsByLessonIdDTO(int lessonId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select q from Question q where q.lesson.id =:id");
            String sql = sb.toString();
            Query query = em.createQuery(sql, Question.class);
            query.setParameter("id", lessonId);
            query.setMaxResults(3);
            return (List<QuestionDTO>) query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void addQuestion(Question question) {
        em.persist(question);
    }

    public void updateQuestion(Question question) {
        em.merge(question);
        em.flush();
    }

    public Question getQuestionByQuestionId(int questionId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select q from Question q where question_id =:id");
            String sql = sb.toString();
            Query query = em.createQuery(sql, Question.class);
            query.setParameter("id", questionId);
//            query.setMaxResults(3);
            return (Question) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public QuestionDTO getQuestionByQuestionIdDto(int questionId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select q from Question q where question_id =:id");
            String sql = sb.toString();
            Query query = em.createQuery(sql, Question.class);
            query.setParameter("id", questionId);
//            query.setMaxResults(3);
            return (QuestionDTO) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Question> getByPagingRequest(PagingRequest pagingRequest, int lessonId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select q from Question q where lesson_id=:id");
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
            sb.append(" ORDER BY " + "q." + column.getData() + " " + order.getDir());

            String sql = sb.toString();
            Query query = em.createQuery(sql, Question.class);
            query.setParameter("id", lessonId);
            query.setFirstResult(pagingRequest.getStart());
            query.setMaxResults(pagingRequest.getLength());
            return (List<Question>) query.getResultList();
        } catch (NoResultException exception) {
            return null;
        }
    }

    public long getQuestionCountByPagingRequest(PagingRequest pagingRequest, int lessonId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT count(q) FROM Question q where lesson_id=:id");
            // append filtering
            if (pagingRequest.getSearch() != null
                    && StringUtils.hasLength(pagingRequest.getSearch().getValue())) {
                String key = "'%" + pagingRequest.getSearch().getValue().toLowerCase() + "%'";
                sb.append(" AND lower(q.question) LIKE " + key);
                sb.append(" OR lower(q.answer) LIKE " + key);
            }

            String sql = sb.toString();
            Query query = em.createQuery(sql, Long.class);
            query.setParameter("id", lessonId);
            return (long) query.getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }

    public void deleteQuestion(int questionId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from Question where id=:id");
            Query query = em.createQuery(sb.toString());
            query.setParameter("id", questionId);
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}