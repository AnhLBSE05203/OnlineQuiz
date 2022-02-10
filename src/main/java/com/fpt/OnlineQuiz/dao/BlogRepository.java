package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.model.Blog;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    public ArrayList<Blog> getAllBlog(){
        try{
            BufferedReader buffer  = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_ALL_BLOG_LIST)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while((line = buffer.readLine()) !=null){
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            //String sql = "SELECT a FROM Blog a";
            Query query = em.createQuery(sql);
            ArrayList<Blog> blogList = (ArrayList<Blog>) query.getSingleResult();
            return  blogList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
