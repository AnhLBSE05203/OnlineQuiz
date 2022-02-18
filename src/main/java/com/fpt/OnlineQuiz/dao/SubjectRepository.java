package com.fpt.OnlineQuiz.dao;


import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.stereotype.Repository;

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
     * @param number how many Subjects are retrieved
     * @return
     */
    public List<Subject> getTopNumberOfSubjects(int number) {
        try {
            BufferedReader buffer  = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_ALL_SUBJECTS)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while((line = buffer.readLine()) !=null){
                sb.append(" ").append(line);
            }
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
            BufferedReader buffer  = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_ALL_SUBJECTS)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while((line = buffer.readLine()) !=null){
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            Query query = em.createQuery(sql, Subject.class);
            return (List<Subject>) query.getResultList();
        } catch (NoResultException | IOException e) {
            return null;
        }
    }
    public Subject getSubjectById(int id) {
        try {
            BufferedReader buffer  = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_FIND_SUBJECT_BY_ID)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while((line = buffer.readLine()) !=null){
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

    public void update(Subject subject) {
        em.merge(subject);
        em.flush();
    }
}
