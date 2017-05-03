package com.schedule.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import com.schedule.model.Teacher;

@Repository
public class TeacherDaoImpl implements TeacherDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Teacher findById(int teacherId) {
		return entityManager.find(Teacher.class, teacherId);
	}

	@Override
	public Teacher findByEmail(String email) {
		TypedQuery<Teacher> query = entityManager.createNamedQuery("findTeacherByEmail", Teacher.class);
		query.setParameter("email", email);
		Teacher teacher = new Teacher();
		try {
			teacher = query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
		return teacher;
	}

	@Override
	public void save(Teacher teacher) {
		entityManager.persist(entityManager.contains(teacher) ? teacher : entityManager.merge(teacher));
	}

	@Override
	public void update(Teacher teacher) {
		entityManager.merge(teacher);
	}

	@Override
	public void delete(Teacher teacher) {
		entityManager.remove(entityManager.contains(teacher) ? teacher : entityManager.merge(teacher));
	}
}
