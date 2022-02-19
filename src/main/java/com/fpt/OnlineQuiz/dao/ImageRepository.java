package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.model.Image;
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

@Transactional
@Repository
public class ImageRepository {
    @PersistenceContext
    EntityManager em;

    public Image getById(int id) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_IMAGE_BY_ID)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = buffer.readLine()) != null) {
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            Query query = em.createQuery(sql, Image.class);
            query.setParameter("id", id);
            return (Image) query.getSingleResult();
        } catch (NoResultException | IOException e) {
            return null;
        }
    }

    public void addImage(Image image) {
        em.persist(image);
    }
}
