package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.model.Blog;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Optional;

@Repository
@Transactional
public class BlogRepository{
    @PersistenceContext//create object when session run
    private EntityManager em;

    public ArrayList<Blog> getAllBlog() {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_ALL_BLOG_LIST)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            //String sql = "SELECT a FROM Blog a";
            Query query = em.createQuery(sql, Blog.class);
            ArrayList<Blog> blogList = (ArrayList<Blog>) query.getResultList();
            return blogList;
        } catch (NoResultException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Blog> getBlogByIndexPage(int pageindex){
        try {
            String sql = "Select b From Blog b";
            Query query = em.createQuery(sql,Blog.class);
            int pageSize = 2;
            query.setFirstResult((pageindex - 1) * pageSize);
            query.setMaxResults(pageSize);
            ArrayList<Blog> blogList = (ArrayList<Blog>) query.getResultList();
            return blogList;
        } catch (NoResultException e) {
            return null;
        }
    }
    public Long countBlog(){
        try {
            String sql = "Select COUNT(a) FROM Blog a";
            Query query = em.createQuery(sql,Long.class);
            return (Long) query.getSingleResult();
        }catch (NoResultException e){
            return 0l;
        }
    }
}
