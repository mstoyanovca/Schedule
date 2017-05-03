package com.schedule.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import com.schedule.model.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager entityManager;
	// private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

	@Override
	public User findById(int userId) {
		return entityManager.find(User.class, userId);
	}

	@Override
	public User findByEmail(String email) {
		TypedQuery<User> query = entityManager.createNamedQuery("findUserByEmail", User.class);
		query.setParameter("email", email);
		User user = new User();
		// logger.info("Find a user by email " + email);
		try {
			user = query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
		return user;
	}

	@Override
	public User findByToken(String token) {
		TypedQuery<User> query = entityManager.createNamedQuery("findUserByToken", User.class);
		query.setParameter("token", token);
		User user = new User();
		// logger.info("Find a user by token = " + token);
		try {
			user = query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
		return user;
	}

	@Override
	public List<User> findAll() {
		TypedQuery<User> query = entityManager.createNamedQuery("findAllUsers", User.class);
		List<User> users = query.getResultList();
		return users;
	}

	@Override
	public void save(User user) {
		entityManager.persist(entityManager.contains(user) ? user : entityManager.merge(user));
	}

	@Override
	public void update(User user) {
		entityManager.merge(user);
	}
}
