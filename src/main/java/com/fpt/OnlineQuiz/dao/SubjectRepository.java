package com.fpt.OnlineQuiz.dao;


import com.fpt.OnlineQuiz.model.Subject;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
}
