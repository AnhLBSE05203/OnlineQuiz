package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.model.Course;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Repository
@Transactional
public class CourseRepository {
    @PersistenceContext
    EntityManager em;

    public List<Course> getTopCourses(int number){
        try {
            BufferedReader buffer  = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_TOP_COURSES)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while((line = buffer.readLine()) !=null){
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            Query query = em.createQuery(sql, Course.class);
            query.setMaxResults(number);
            return (List<Course>) query.getResultList();
        } catch (NoResultException | IOException e) {
            return null;
        }
    }
}
