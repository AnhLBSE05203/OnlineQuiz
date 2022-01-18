package com.fpt.OnlineQuiz.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.stereotype.Repository;

import com.fpt.OnlineQuiz.model.Role;

@Repository
@Transactional
public class RoleRepository {
	@PersistenceContext
	private EntityManager em;

	/**
	 * Find All Roles from DB
	 * @return
	 */
	public List<Role> findAll() {
		try {
			BufferedReader buffer  = new BufferedReader(new InputStreamReader(
					this.getClass().getResourceAsStream(Constants.SQL_PATH_FIND_ALL_ROLES)));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while((line = buffer.readLine()) !=null){
				sb.append(" ").append(line);
			}
			String sql = sb.toString();
			Query query = em.createQuery(sql, Role.class);

			return (List<Role>) query.getResultList();
		} catch (NoResultException | IOException e) {
			return null;
		}
	}
}
