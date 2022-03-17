package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.dto.paging.Column;
import com.fpt.OnlineQuiz.dto.paging.Order;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Account;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.*;
import java.util.List;

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
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_FIND_ACCOUNT_BY_EMAIL)));
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
    }//thêm vào entitymanagement và cũng thêm vào database luôn

    /**
     * Update account to DB
     * @param account User's account
     */
    public void updateAccount(Account account) {
        this.em.merge(account);
        em.flush();//Thực hiện ngay ko cần tích trữ câu lệnh
    }

    /**
     * Find Account By Token which is sent to the User
     * @param tokenString token sent to the User
     * @return User's account
     */
    public Account findByToken(String tokenString, String tokenType) {
        try {
            BufferedReader buffer  = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_FIND_ACCOUNT_BY_TOKEN)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while((line = buffer.readLine()) !=null){
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            Query query = em.createQuery(sql, Account.class);
            query.setParameter("token", tokenString);
            query.setParameter("tokenType", tokenType);
            return (Account) query.getSingleResult();
        } catch (NoResultException | IOException e) {
            return null;
        }
    }

    public List<Account> getAllAccountAdmin(PagingRequest pagingRequest) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_ALL_ACCOUNT)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            //append filtering
            if (pagingRequest.getSearch() != null
                    && StringUtils.hasLength(pagingRequest.getSearch().getValue())) {
                String key = "'%" + pagingRequest.getSearch().getValue().toLowerCase() + "%'";
//                sb.append(" AND lower(b.thumbnail.src) LIKE " + key);
//                sb.append(" OR lower(b.content) LIKE " + key);
//                sb.append(" OR lower(b.title) LIKE " + key);
            }
            // append sorting
            Order order = pagingRequest.getOrder().get(0);
            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns().get(columnIndex);
            sb.append(" ORDER BY " + "a." + column.getData() + " " + order.getDir());

            String sql = sb.toString();
            //String sql = "SELECT a FROM Account a";
            Query query = em.createQuery(sql, Account.class);
            query.setFirstResult(pagingRequest.getStart());
            query.setMaxResults(pagingRequest.getLength());
            return (List<Account>) query.getResultList();
        } catch (NoResultException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getAllAccountAdminCountTotalRecord(PagingRequest pagingRequest) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_ALL_ACCOUNT_COUNT)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            //append filtering
            if (pagingRequest.getSearch() != null
                    && StringUtils.hasLength(pagingRequest.getSearch().getValue())) {
                String key = "'%" + pagingRequest.getSearch().getValue().toLowerCase() + "%'";
//                sb.append(" AND lower(b.thumbnail.src) LIKE " + key);
//                sb.append(" OR lower(b.content) LIKE " + key);
//                sb.append(" OR lower(b.title) LIKE " + key);
            }

            String sql = sb.toString();
            //String sql = "SELECT count(a) FROM Account b";
            Query query = em.createQuery(sql, Long.class);
            return (long) query.getSingleResult();
        } catch (NoResultException | IOException e) {
            return 0;
        }
    }
}