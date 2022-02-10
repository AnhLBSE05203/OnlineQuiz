package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.model.Blog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Repository
@Transactional
public class BlogRepository{
    @PersistenceContext//create object when session run
    private EntityManager em;

    public ArrayList<Blog> getAllBlog(){
        String sql = "SELECT * FROM Blog";
        Query query = em.createQuery(sql);
        return null;
    }
}
