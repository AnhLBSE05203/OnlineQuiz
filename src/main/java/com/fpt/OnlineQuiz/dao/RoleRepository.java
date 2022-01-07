package com.fpt.OnlineQuiz.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

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
			String sql = "SELECT r from Role r";
			Query query = em.createQuery(sql, Role.class);

			return (List<Role>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
