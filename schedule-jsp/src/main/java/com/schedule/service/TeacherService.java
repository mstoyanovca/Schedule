package com.schedule.service;

import com.schedule.model.Teacher;

public interface TeacherService {

	public Teacher findById(int teacherId);

	public Teacher findByEmail(String email);

	public void save(Teacher teacher);

	public void update(Teacher teacher);

	public void delete(Teacher teacher);
}