package com.schedule.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.schedule.dao.LessonDao;
import com.schedule.model.Lesson;

@Transactional
@Service("lessonService")
public class LessonServiceImpl implements LessonService {

	@Autowired
	private LessonDao lessonDao;

	@Override
	public List<Lesson> findAllByTeacherId(int teacherId) {
		return lessonDao.findAllByTeacherId(teacherId);
	}

	@Override
	public List<Lesson> findAllByStudentId(int studentId) {
		return lessonDao.findAllByStudentId(studentId);
	}

	@Override
	public Lesson findById(int lessonId) {
		return lessonDao.findById(lessonId);
	}

	@Override
	public void save(Lesson lesson) {
		lessonDao.save(lesson);
	}

	@Override
	public void update(Lesson lesson) {
		lessonDao.update(lesson);
	}

	@Override
	public void delete(Lesson lesson) {
		lessonDao.delete(lesson);
	}
}
