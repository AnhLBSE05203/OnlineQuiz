package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.model.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.*;

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
            BufferedReader buffer  = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream("/static/sql/findAccountByEmail.sql")));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while((line = buffer.readLine()) !=null){
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            Query query = em.createQuery(sql, Account.class);
            query.setParameter("email", email);

            return (Account) query.getSingleResult();
        } catch (NoResultException | IOException e) {
            return null;
        }
    }

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
            BufferedReader buffer  = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream("/static/sql/findByResetPasswordToken.sql")));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while((line = buffer.readLine()) !=null){
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            Query query = em.createQuery(sql, Account.class);
            query.setParameter("token", token);

            return (Account) query.getSingleResult();
        } catch (NoResultException | IOException e) {
            return null;
        }
    }
    /**
     * Find Account By Generated ConfirmToken which is sent to the User
     * @param token confirm token sent to the User
     * @return User's account
     */
    public Account findByConfirmToken(String token) {
        try {
            BufferedReader buffer  = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream("/static/sql/findByConfirmToken.sql")));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while((line = buffer.readLine()) !=null){
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            Query query = em.createQuery(sql, Account.class);
            query.setParameter("token", token);

            return (Account) query.getSingleResult();
        } catch (NoResultException | IOException e) {
            return null;
        }
    }
}