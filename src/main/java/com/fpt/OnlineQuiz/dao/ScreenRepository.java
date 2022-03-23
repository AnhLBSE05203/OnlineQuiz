package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.model.Screen;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ScreenRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Screen> findAll() {
        try {
            StringBuilder sb = new StringBuilder(Constants.SQL_GET_ALL_SCREENS);

            String sql = sb.toString();
            Query query = em.createQuery(sql, Screen.class);

            return (List<Screen>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}

