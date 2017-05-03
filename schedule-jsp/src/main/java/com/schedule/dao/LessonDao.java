package com.schedule.dao;

import java.util.List;
import com.schedule.model.Lesson;

public interface LessonDao {

	public List<Lesson> findAllByTeacherId(int teacherId);

	public List<Lesson> findAllByStudentId(int studentId);

	public Lesson findById(int lessonId);

	public void save(Lesson lesson);

	public void update(Lesson lesson);

	public void delete(Lesson lesson);
}
