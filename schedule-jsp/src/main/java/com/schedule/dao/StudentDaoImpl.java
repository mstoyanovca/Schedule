package com.schedule.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import com.schedule.model.Student;

@Repository("studentDao")
public class StudentDaoImpl implements StudentDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Student> findAll(int teacherId) {
		TypedQuery<Student> query = entityManager.createNamedQuery("findAllStudentsByTeacherId", Student.class);
		query.setParameter("teacherId", teacherId);
		List<Student> students = query.getResultList();
		return students;
	}

	@Override
	public Student findById(int studentId) {
		return entityManager.find(Student.class, studentId);
	}

	@Override
	public void save(Student student) {
		entityManager.persist(entityManager.contains(student) ? student : entityManager.merge(student));
	}

	@Override
	public void update(Student student) {
		entityManager.merge(student);
	}

	@Override
	public void delete(Student student) {
		entityManager.remove(entityManager.contains(student) ? student : entityManager.merge(student));
	}
}
