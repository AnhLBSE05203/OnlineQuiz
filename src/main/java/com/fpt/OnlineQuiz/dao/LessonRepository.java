package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.dto.paging.Column;
import com.fpt.OnlineQuiz.dto.paging.Order;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Lesson;
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
import java.util.List;

@Repository
@Transactional
public class LessonRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Lesson> getByPagingRequest(PagingRequest pagingRequest) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_ALL_LESSONS)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            // append filtering
            if (pagingRequest.getSearch() != null
                    && StringUtils.hasLength(pagingRequest.getSearch().getValue())) {
                String key = "'%" + pagingRequest.getSearch().getValue().toLowerCase() + "%'";
                sb.append(" AND lower(l.image.src) LIKE " + key);
                sb.append(" OR lower(l.name) LIKE " + key);
            }
            // append sorting
            Order order = pagingRequest.getOrder().get(0);
            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns().get(columnIndex);
            sb.append(" ORDER BY " + "l." + column.getData() + " " + order.getDir());

            String sql = sb.toString();
            Query query = em.createQuery(sql, Lesson.class);
            query.setFirstResult(pagingRequest.getStart());
            query.setMaxResults(pagingRequest.getLength());
            return (List<Lesson>) query.getResultList();
        } catch (NoResultException | IOException e) {
            return null;
        }
    }

    public long getLessonCountByPagingRequest(PagingRequest pagingRequest) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_LESSON_COUNT)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            // append filtering
            if (pagingRequest.getSearch() != null
                    && StringUtils.hasLength(pagingRequest.getSearch().getValue())) {
                String key = "'%" + pagingRequest.getSearch().getValue().toLowerCase() + "%'";
                sb.append(" AND lower(l.image.src) LIKE " + key);
                sb.append(" OR lower(l.name) LIKE " + key);
            }

            String sql = sb.toString();
            Query query = em.createQuery(sql, Long.class);
            return (long) query.getSingleResult();
        } catch (NoResultException | IOException e) {
            return 0;
        }
    }

    public void addLesson(Lesson lesson) {
        em.persist(lesson);
    }

    public void updateLesson(Lesson lesson) {
        em.merge(lesson);
        em.flush();
    }
    public Lesson getLessonByLessonId(int id){
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select l from Lesson l where lesson_id = :id");
            Query query = em.createQuery(sb.toString());
            query.setParameter("id", id);
            return (Lesson)query.getSingleResult();
        }catch (NoResultException exception){
            return null;
        }
    }
}
