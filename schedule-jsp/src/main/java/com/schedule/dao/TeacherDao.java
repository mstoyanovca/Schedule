package com.schedule.dao;

import com.schedule.model.Teacher;

public interface TeacherDao {

	public Teacher findById(int teacherId);

	public Teacher findByEmail(String email);

	public void save(Teacher teacher);

	public void update(Teacher teacher);
	
	public void delete(Teacher teacher);
}