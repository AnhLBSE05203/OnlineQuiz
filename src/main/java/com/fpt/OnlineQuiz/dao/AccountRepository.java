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

    /**
     * find Account by Email
     * @param email user's email
     * @return Account user's account
     */
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

    /**
     * add new Account to DB
     * @param account user's account
     */
    public void addAccount(Account account) {

        this.em.persist(account);
    }

    /**
     * Update account to DB
     * @param account User's account
     */
    public void updateAccount(Account account) {
        this.em.merge(account);
    }

    /**
     * Find Account By Generated ResetPasswordToken which is sent to the User
     * @param token reset password token sent to the User
     * @return User's account
     */
    public Account findByResetPasswordToken(String token) {
        try {
            String sql = "SELECT a from Account a "
                    + " WHERE a.resetPasswordToken = :token";
            Query query = em.createQuery(sql, Account.class);
            query.setParameter("token", token);

            return (Account) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}