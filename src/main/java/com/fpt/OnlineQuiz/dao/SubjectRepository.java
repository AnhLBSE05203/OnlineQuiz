package com.fpt.OnlineQuiz.dao;


import com.fpt.OnlineQuiz.dto.paging.Column;
import com.fpt.OnlineQuiz.dto.paging.Order;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.utils.Constants;
import com.fpt.OnlineQuiz.utils.Utils;
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
import java.util.List;

@Repository
@Transactional
public class SubjectRepository {
    @PersistenceContext
    private EntityManager em;


    /**
     * Get a number of Subjects to feature on Home page
     *
     * @param number how many Subjects are retrieved
     * @return
     */
    public List<Subject> getTopNumberOfSubjects(int number) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_ALL_SUBJECTS)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            sb.append(Constants.SQL_CONDITION_STATUS_DEFAULT);
            String sql = sb.toString();
            Query query = em.createQuery(sql, Subject.class);
            query.setMaxResults(number);
            return (List<Subject>) query.getResultList();
        } catch (NoResultException | IOException e) {
            return null;
        }
    }

    public List<Subject> findAllSubjects() {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_ALL_SUBJECTS)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            Query query = em.createQuery(sql, Subject.class);
            return (List<Subject>) query.getResultList();
        } catch (NoResultException | IOException e) {
            return null;
        }
    }

    public List<Subject> findAllSubjectsByPaging(int pageIndex, int pageSize) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_ALL_SUBJECTS)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            Query query = em.createQuery(sql, Subject.class);
            query.setFirstResult((pageIndex - 1) * pageSize);
            query.setMaxResults(pageSize);
            List<Subject> subjectList = (List<Subject>) query.getResultList();
            return (List<Subject>) query.getResultList();
        } catch (NoResultException | IOException e) {
            return null;
        }
    }

    public Long countSubject() {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_SUBJECT_COUNT)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            Query query = em.createQuery(sql, Long.class);
            return (Long) query.getSingleResult();
        } catch (NoResultException | IOException e) {
            return 0l;
        }
    }

    public List<Subject> getByPagingRequest(PagingRequest pagingRequest) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_ALL_SUBJECTS)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            // append filtering
            if (pagingRequest.getSearch() != null
                    && StringUtils.hasLength(pagingRequest.getSearch().getValue())) {
                String key = "'%" + pagingRequest.getSearch().getValue().toLowerCase() + "%'";
                sb.append(" AND lower(s.name) LIKE " + key);
                sb.append(" OR lower(s.subjectInfo) LIKE " + key);
                sb.append(" OR lower(s.learnAfter) LIKE " + key);
                if (Utils.isInteger(pagingRequest.getSearch().getValue(), 10)) {
                    sb.append(" OR s.totalCourse = " + pagingRequest.getSearch().getValue());
                    sb.append(" OR s.id = " + pagingRequest.getSearch().getValue());
                }
            }
            // append sorting
            Order order = pagingRequest.getOrder().get(0);
            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns().get(columnIndex);
            sb.append(" ORDER BY " + "s." + column.getData() + " " + order.getDir());

            String sql = sb.toString();
            Query query = em.createQuery(sql, Subject.class);
            query.setFirstResult(pagingRequest.getStart());
            query.setMaxResults(pagingRequest.getLength());
            return (List<Subject>) query.getResultList();
        } catch (NoResultException | IOException e) {
            return null;
        }
    }

    public long getSubjectCountByPagingRequest(PagingRequest pagingRequest) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_SUBJECT_COUNT)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            // append filtering
            if (pagingRequest.getSearch() != null
                    && StringUtils.hasLength(pagingRequest.getSearch().getValue())) {
                String key = "'%" + pagingRequest.getSearch().getValue().toLowerCase() + "%'";
                sb.append(" AND lower(s.name) LIKE " + key);
                sb.append(" OR lower(s.subjectInfo) LIKE " + key);
                sb.append(" OR lower(s.learnAfter) LIKE " + key);
                if (Utils.isInteger(pagingRequest.getSearch().getValue(), 10)) {
                    sb.append(" OR s.totalCourse = " + pagingRequest.getSearch().getValue());
                    sb.append(" OR s.id = " + pagingRequest.getSearch().getValue());
                }

            }

            String sql = sb.toString();
            Query query = em.createQuery(sql, Long.class);
            return (long) query.getSingleResult();
        } catch (NoResultException | IOException e) {
            return 0;
        }
    }

    public Subject getSubjectById(int id) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_FIND_SUBJECT_BY_ID)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            Query query = em.createQuery(sql, Subject.class);
            query.setParameter("id", id);
            return (Subject) query.getSingleResult();
        } catch (NoResultException | IOException e) {
            return null;
        }
    }

    public Subject getSubjectByNameLower(String name) {
        try {
            StringBuilder sb = new StringBuilder("SELECT s FROM Subject s WHERE lower(s.name) = lower(:name)");
            String sql = sb.toString();
            Query query = em.createQuery(sql, Subject.class);
            query.setParameter("name", name);
            List<Subject> subjects = (List<Subject>) query.getResultList();
            if (subjects.size() > 0) {
                return subjects.get(0);
            }
        } catch (NoResultException e) {
            return null;
        }
        return null;
    }

    public void updateSubject(Subject subject) {
        em.merge(subject);
        em.flush();
    }

    public void addSubject(Subject subject) {
        em.persist(subject);
    }
}
