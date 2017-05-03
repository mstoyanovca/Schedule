package com.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.schedule.dao.TeacherDao;
import com.schedule.model.Teacher;

@Transactional
@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherDao teacherDao;

	@Override
	public Teacher findById(int teacherId) {
		return teacherDao.findById(teacherId);
	}

	@Override
	public Teacher findByEmail(String email) {
		return teacherDao.findByEmail(email);
	}

	@Override
	public void save(Teacher teacher) {
		teacherDao.save(teacher);
	}

	@Override
	public void update(Teacher teacher) {
		teacherDao.update(teacher);
	}

	@Override
	public void delete(Teacher teacher) {
		teacherDao.delete(teacher);
	}
}
