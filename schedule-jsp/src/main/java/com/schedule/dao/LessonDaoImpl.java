package com.schedule.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import com.schedule.model.Lesson;

@Repository("lessonDao")
public class LessonDaoImpl implements LessonDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Lesson> findAllByTeacherId(int teacherId) {
		TypedQuery<Lesson> query = entityManager.createNamedQuery("findAllLessonsByTeacherId", Lesson.class);
		query.setParameter("teacherId", teacherId);
		List<Lesson> lessons = query.getResultList();
		return lessons;
	}

	@Override
	public List<Lesson> findAllByStudentId(int studentId) {
		TypedQuery<Lesson> query = entityManager.createNamedQuery("findAllLessonsByStudentId", Lesson.class);
		query.setParameter("studentId", studentId);
		List<Lesson> lessons = query.getResultList();
		return lessons;
	}

	@Override
	public Lesson findById(int lessonId) {
		return entityManager.find(Lesson.class, lessonId);
	}

	@Override
	public void save(Lesson lesson) {
		entityManager.persist(entityManager.contains(lesson) ? lesson : entityManager.merge(lesson));
	}

	@Override
	public void update(Lesson lesson) {
		entityManager.merge(lesson);
	}

	@Override
	public void delete(Lesson lesson) {
		// entityManager.remove(lesson);
		entityManager.remove(entityManager.contains(lesson) ? lesson : entityManager.merge(lesson));
	}
}
