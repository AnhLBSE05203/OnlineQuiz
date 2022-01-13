package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.model.Token;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Repository
@Transactional
public class TokenRepository {
    @PersistenceContext
    private EntityManager em;

    /**
     * Add Token
     * @param token
     */
    public void addToken(Token token){
        this.em.persist(token);
    }

    /**
     * Delete Token
     * @param token
     */
    public void deleteToken(Token token){
        this.em.remove(token);
        this.em.flush();
        this.em.clear();
    }

    /**
     * find token by generated token string
     * @param tokenString generated token string
     * @return Token
     */
    public Token findByTokenString(String tokenString) {
        try {
            BufferedReader buffer  = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream("/static/sql/findTokenByTokenString.sql")));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while((line = buffer.readLine()) !=null){
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            Query query = em.createQuery(sql, Account.class);
            query.setParameter("tokenString", tokenString);

            return (Token) query.getSingleResult();
        } catch (NoResultException | IOException e) {
            return null;
        }
    }
}
