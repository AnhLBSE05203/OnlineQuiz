package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class RoleRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Role> findAll() {
        try {
            String sql = "SELECT r from Role r";
            Query query = em.createQuery(sql, Role.class);

            return (List<Role>) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
