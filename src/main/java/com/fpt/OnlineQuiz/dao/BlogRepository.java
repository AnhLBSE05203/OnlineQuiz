package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.dto.paging.Column;
import com.fpt.OnlineQuiz.dto.paging.Order;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Blog;
import com.fpt.OnlineQuiz.utils.Constants;
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
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class BlogRepository {
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
            //String sql = "SELECT b FROM Blog b";
            Query query = em.createQuery(sql, Blog.class);
            ArrayList<Blog> blogList = (ArrayList<Blog>) query.getResultList();
            return blogList;
        } catch (NoResultException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Blog> getAllBlogAdmin(PagingRequest pagingRequest) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_ALL_BLOG_LIST)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            //append filtering
            if (pagingRequest.getSearch() != null
                    && StringUtils.hasLength(pagingRequest.getSearch().getValue())) {
                String key = "'%" + pagingRequest.getSearch().getValue().toLowerCase() + "%'";
                sb.append(" AND (lower(b.content) LIKE " + key);
                sb.append(" OR lower(b.title) LIKE " + key +" )");
            }
            // append sorting
            Order order = pagingRequest.getOrder().get(0);
            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns().get(columnIndex);
            sb.append(" ORDER BY " + "b." + column.getData() + " " + order.getDir());

            String sql = sb.toString();
            //String sql = "SELECT b FROM Blog b";
            Query query = em.createQuery(sql, Blog.class);
            query.setFirstResult(pagingRequest.getStart());
            query.setMaxResults(pagingRequest.getLength());
            return (List<Blog>) query.getResultList();
        } catch (NoResultException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getAllBlogAdminCountTotalRecord(PagingRequest pagingRequest) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_BLOG_COUNT)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            //append filtering
            if (pagingRequest.getSearch() != null
                    && StringUtils.hasLength(pagingRequest.getSearch().getValue())) {
                String key = "'%" + pagingRequest.getSearch().getValue().toLowerCase() + "%'";
                sb.append(" AND (lower(b.content) LIKE " + key);
                sb.append(" OR lower(b.title) LIKE " + key +" )");
            }

            String sql = sb.toString();
            //String sql = "SELECT count(b) FROM Blog b";
            Query query = em.createQuery(sql, Long.class);
            return (long) query.getSingleResult();
        } catch (NoResultException | IOException e) {
            return 0;
        }
    }

    public ArrayList<Blog> getBlogByIndexPage(int pageIndex, int pageSize) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_ALL_BLOG_LIST)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            Query query = em.createQuery(sql, Blog.class);
            query.setFirstResult((pageIndex - 1) * pageSize);
            query.setMaxResults(pageSize);
            ArrayList<Blog> blogList = (ArrayList<Blog>) query.getResultList();
            return blogList;
        } catch (NoResultException | IOException e) {
            return null;
        }
    }

    public Long countBlog() {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_BLOG_COUNT)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            Query query = em.createQuery(sql, Long.class);
            return (Long) query.getSingleResult();
        } catch (NoResultException | IOException e) {
            return 0l;
        }
    }

    public Blog getDetailBlog(int id) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_DETAIL_BLOG)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            //String sql = "SELECT b FROM Blog b";
            Query query = em.createQuery(sql, Blog.class);
            query.setParameter("id", id);
            Blog blog = (Blog) query.getSingleResult();
            return blog;
        } catch (NoResultException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateBlog(Blog blog) {
        em.merge(blog);
        em.flush();
    }

    public void addBlog (Blog blog) {
        em.persist(blog);
    }
}
