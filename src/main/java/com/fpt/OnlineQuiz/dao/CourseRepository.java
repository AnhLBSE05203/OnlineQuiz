package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.dto.CourseFeaturedDTO;
import com.fpt.OnlineQuiz.dto.paging.Column;
import com.fpt.OnlineQuiz.dto.paging.Order;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Course;
import com.fpt.OnlineQuiz.utils.Constants;
import com.fpt.OnlineQuiz.utils.Utils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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

    public void updateCourse(Course course) {
        em.merge(course);
        em.flush();
    }

    public void addCourse(Course course) {
        em.persist(course);
    }

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
     * @param accountId user's id
     * @return
     */
    public List<Course> getTop3Courses(int accountId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.SQL_GET_COURSES_BY_ACCOUNT);
            String sql = sb.toString();
            Query query = em.createQuery(sql, Collection.class);
            query.setParameter("id", accountId);
            query.setMaxResults(3);
            return (List<Course>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Course> getNext3Courses(int accountId, int start) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.SQL_GET_COURSES_BY_ACCOUNT);
            String sql = sb.toString();
            int size = 3;
            Query query = em.createQuery(sql, Collection.class);
            query.setParameter("id", accountId);
            query.setFirstResult(start);
            query.setMaxResults(size);
            return (List<Course>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Course getCourseById(int id) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.SQL_GET_COURSE_BY_ID);
            String sql = sb.toString();
            Query query = em.createQuery(sql, Course.class);
            query.setParameter("id", id);
            return (Course) query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Course> getCoursesByPagingRequest(int subjectId, PagingRequest pagingRequest) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.SQL_GET_COURSE_BY_SUBJECT_ID);

            if (pagingRequest.getSearch() != null
                    && StringUtils.hasLength(pagingRequest.getSearch().getValue())) {
                String value = pagingRequest.getSearch().getValue();
                String key = "'%" + value.toLowerCase() + "%'";
                sb.append(" AND lower(c.name) LIKE " + key);
                sb.append(" OR lower(c.description) LIKE " + key);
                if (Utils.isNumeric(value)) {
                    sb.append(" OR c.price = " + value);
                    if (Utils.isInteger(value, 10)) {
                        sb.append(" OR c.lessonTotal = " + value);
                    }
                }
            }
            // append sorting
            Order order = pagingRequest.getOrder().get(0);
            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns().get(columnIndex);
            sb.append(" ORDER BY " + "c." + column.getData() + " " + order.getDir());
            String sql = sb.toString();
            Query query = em.createQuery(sql, Course.class);
            query.setFirstResult(pagingRequest.getStart());
            query.setMaxResults(pagingRequest.getLength());
            query.setParameter("subjectId", subjectId);
            return (List<Course>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Long getCoursesCountByPagingRequest(int subjectId, PagingRequest pagingRequest) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.SQL_GET_COURSE_COUNT_BY_SUBJECT_ID);

            if (pagingRequest.getSearch() != null
                    && StringUtils.hasLength(pagingRequest.getSearch().getValue())) {
                String value = pagingRequest.getSearch().getValue();
                String key = "'%" + value.toLowerCase() + "%'";
                sb.append(" AND lower(c.name) LIKE " + key);
                sb.append(" OR lower(c.description) LIKE " + key);
                if (Utils.isNumeric(value)) {
                    sb.append(" OR c.price = " + value);
                    if (Utils.isInteger(value, 10)) {
                        sb.append(" OR c.lessonTotal = " + value);
                    }
                }
            }
            String sql = sb.toString();
            Query query = em.createQuery(sql, Long.class);
            query.setParameter("subjectId", subjectId);
            return (long) query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0l;
    }

    public long getDuplicateCount(String name, int subjectId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.SQL_GET_COURSE_COUNT_BY_SUBJECT_ID);

            // filter by name
            if (name != null
                    && StringUtils.hasLength(name)) {
                String key = "'%" + name.toLowerCase() + "%'";
                sb.append(" AND lower(c.name) LIKE " + key);
            }
            String sql = sb.toString();
            Query query = em.createQuery(sql, Long.class);
            query.setParameter("subjectId", subjectId);
            return (long) query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0l;
    }
    public void addRegistrationCourse(List<Course> list, int accountId){
        for(int i = 0; i < list.size(); i++){
            try {
                StringBuilder sb = new StringBuilder();
                //todo Fix sql
                sb.append("INSERT INTO account_course(course_id, account_id) values (3, 571)");
                Query query = em.createQuery(sb.toString());
//                query.setParameter("courseId", list.get(i).getId());
//                query.setParameter("accountId", accountId);
                query.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public List<Course> getTop3CoursesBySubjectId(int subjectId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select c from Course c where c.subject.id =:id");
            String sql = sb.toString();
            Query query = em.createQuery(sql, Course.class);
            query.setParameter("id", subjectId);
            query.setMaxResults(3);
            return (List<Course>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Course> getNext3CoursesBySubjectId(int subjectId, int start) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select c from Course c where c.subject.id =:id");
            String sql = sb.toString();
            int size = 3;
            Query query = em.createQuery(sql, Course.class);
            query.setParameter("id", subjectId);
            query.setFirstResult(start);
            query.setMaxResults(size);
            return (List<Course>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
