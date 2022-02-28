package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.dto.CourseFeaturedDTO;
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
import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public class CourseRepository {
    @PersistenceContext
    EntityManager em;

    public List<CourseFeaturedDTO> getFeaturedCourses(int number) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_FEATURED_COURSES)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            Query query = em.createQuery(sql, CourseFeaturedDTO.class);
            query.setMaxResults(number);
            return (List<CourseFeaturedDTO>) query.getResultList();
        } catch (NoResultException | IOException e) {
            return null;
        }
    }

    /**
     * Get a number of Course which user currently registers
     *
     * @param account_id user's id
     * @return
     */
    public List<Course> getTop3Courses(int account_id) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.SQL_GET_COURSES_BY_ACCOUNT);
            String sql = sb.toString();
            Query query = em.createQuery(sql, Collection.class);
            query.setParameter("id", account_id);
            query.setMaxResults(3);
            return (List<Course>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Course> getNext3Courses(int account_id, int start) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.SQL_GET_COURSES_BY_ACCOUNT);
            String sql = sb.toString();
            int size = 3;
            Query query = em.createQuery(sql, Collection.class);
            query.setParameter("id", account_id);
            query.setFirstResult(start);
            query.setMaxResults(size);
            return (List<Course>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
