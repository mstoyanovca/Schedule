package com.mstoyanov.schedule.web;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.mstoyanov.schedule.domain.Phone;
import com.mstoyanov.schedule.domain.Student;
import com.mstoyanov.schedule.domain.StudentRepository;
import com.mstoyanov.schedule.domain.Teacher;
import com.mstoyanov.schedule.domain.TeacherRepository;
import com.mstoyanov.schedule.domain.User;
import com.mstoyanov.schedule.domain.UserRepository;

@RestController
public class StudentRestController {

	private TeacherRepository teacherRepository;
	private StudentRepository studentRepository;
	private UserRepository userRepository;
	private static final Logger logger = LoggerFactory.getLogger(StudentRestController.class);

	@Autowired
	public StudentRestController(TeacherRepository teacherRepository, StudentRepository studentRepository,
			UserRepository userRepository) {
		this.teacherRepository = teacherRepository;
		this.studentRepository = studentRepository;
		this.userRepository = userRepository;
	}

	// find all:
	@RequestMapping(value = "/student", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Student>> findAll(Authentication authentication) {
		String email = authentication.getName();
		User user = userRepository.findDistinctByEmail(email);
		Teacher teacher = user.getTeacher();
		List<Student> students = teacher.getStudents();
		if (students.isEmpty()) {
			logger.info("Students list is empty.");
			return new ResponseEntity<List<Student>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
	}

	// create:
	@RequestMapping(value = "/student/", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Void> createStudent(Authentication authentication, @RequestBody @Valid Student student) {
		String email = authentication.getName();
		User user = userRepository.findDistinctByEmail(email);
		Teacher teacher = user.getTeacher();
		for (Phone phone : student.getPhones()) {
			phone.setStudent(student);
		}
		student.setTeacher(teacher);
		studentRepository.save(student);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	// update:
	@RequestMapping(value = "/student/{studentId}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<Void> updateStudent(Authentication authentication, @PathVariable("studentId") long studentId,
			@RequestBody @Valid Student student) {
		Student currentStudent = null;
		String email = authentication.getName();
		User user = userRepository.findDistinctByEmail(email);
		Teacher teacher = user.getTeacher();
		List<Student> students = teacher.getStudents();
		for (Student s : students) {
			if (studentId == s.getStudentId())
				currentStudent = s;
		}
		if (currentStudent == null) {
			logger.info("Student with studentId = " + studentId + " not found.");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		currentStudent.setName(student.getName());
		currentStudent.getPhones().clear();
		currentStudent.getPhones().addAll(student.getPhones());
		for (Phone phone : student.getPhones()) {
			phone.setStudent(currentStudent);
		}
		currentStudent.setNote(student.getNote());
		studentRepository.save(currentStudent);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	// delete:
	@RequestMapping(value = "/student/{studentId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteStudent(Authentication authentication,
			@PathVariable("studentId") long studentId) {
		Student student = null;
		String email = authentication.getName();
		User user = userRepository.findDistinctByEmail(email);
		Teacher teacher = user.getTeacher();
		List<Student> students = teacher.getStudents();
		for (Student s : students) {
			if (s.getStudentId() == studentId) {
				student = s;
			}
		}
		if (student == null) {
			logger.info("Student with studentId = " + studentId + " not found.");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		students.remove(student);
		teacherRepository.save(teacher);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
