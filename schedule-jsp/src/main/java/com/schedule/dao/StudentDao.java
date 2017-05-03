package com.schedule.dao;

import java.util.List;
import com.schedule.model.Student;

public interface StudentDao {

	public List<Student> findAll(int teacherId);

	public Student findById(int studentId);

	public void save(Student student);

	public void update(Student student);

	public void delete(Student student);
}
