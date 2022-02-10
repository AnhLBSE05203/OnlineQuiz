package com.fpt.OnlineQuiz.dao;

import com.fpt.OnlineQuiz.dto.ExpertFeaturedDTO;
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
public class ExpertRepository {
    @PersistenceContext
    EntityManager em;

    public List<ExpertFeaturedDTO> getFeaturedExperts(int number){
        try {
            BufferedReader buffer  = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(Constants.SQL_PATH_GET_FEATURED_EXPERTS)));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while((line = buffer.readLine()) !=null){
                sb.append(" ").append(line);
            }
            String sql = sb.toString();
            Query query = em.createQuery(sql, ExpertFeaturedDTO.class);
            query.setMaxResults(number);
            return (List<ExpertFeaturedDTO>) query.getResultList();
        } catch (NoResultException | IOException e) {
            return null;
        }
    }
}
