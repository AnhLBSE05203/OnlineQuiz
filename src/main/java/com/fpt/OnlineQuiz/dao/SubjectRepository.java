package com.fpt.OnlineQuiz.dao;


import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Repository
public class SubjectRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Integer> getPackageId(int account_id){
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select package_id from account_package where account_id = " + account_id);
            String sql = sb.toString();
            Query query = em.createQuery(sql);
            return (List<Integer>) query.getResultList();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public List<Subject> getSubjectByPackageId(int package_id){
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from subject where subject_id = " +
                    "(select subject_id from package where package_id = " + package_id +")");
            String sql = sb.toString();
            Query query = em.createQuery(sql);
            return (List<Subject>) query.getResultList();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Get a number of Subjects
     * @param number how many Subjects are retrieved
     * @return
     */
    public List<Subject> getTopSubjects(int number) {
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
}
