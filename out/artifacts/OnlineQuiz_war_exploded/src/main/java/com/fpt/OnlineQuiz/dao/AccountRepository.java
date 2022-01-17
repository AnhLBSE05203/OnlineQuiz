package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.model.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
@Transactional
public class AccountRepository {
    @PersistenceContext
    private EntityManager em;

    public Account findAccountByEmail(String email) {
        try {
            String sql = "SELECT a from Account a "
                    + " WHERE a.email = :email";
            Query query = em.createQuery(sql, Account.class);
            query.setParameter("email", email);

            return (Account) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    };

    public void addAccount(Account account) {

        this.em.persist(account);
    }
}