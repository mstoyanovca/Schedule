package com.schedule.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.schedule.dao.StudentDao;
import com.schedule.model.Student;

@Transactional
@Service("studentService")
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao studentDao;

	@Override
	public List<Student> findAll(int teacherId) {
		return studentDao.findAll(teacherId);
	}

	@Override
	public Student findById(int studentId) {
		return studentDao.findById(studentId);
	}

	@Override
	public void save(Student student) {
		studentDao.save(student);
	}

	@Override
	public void update(Student student) {
		studentDao.update(student);
	}

	@Override
	public void delete(Student student) {
		studentDao.delete(student);
	}
}
