package com.schedule.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import com.schedule.model.Lesson;
import com.schedule.model.Phone;
import com.schedule.model.Profile;
import com.schedule.model.Student;
import com.schedule.service.StudentService;
import com.schedule.service.TeacherService;

@RestController
@SessionAttributes("profile")
public class StudentRESTController {

	@Autowired
	private TeacherService teacherService;
	@Autowired
	private StudentService studentService;
	// private static final Logger logger = Logger.getLogger(StudentRESTController.class);

	// Find all students:
	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public ResponseEntity<List<Student>> findAll(@ModelAttribute("profile") Profile profile) {
		// logger.info("Find all students.");
		List<Student> students = studentService.findAll(profile.getTeacherId());
		if (students.isEmpty()) {
			return new ResponseEntity<List<Student>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
	}

	// Find a student:
	@RequestMapping(value = "/student/{studentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Student> findStudent(@PathVariable("studentId") int studentId) {
		// logger.info("Find a student with studentId = " + studentId);
		Student student = studentService.findById(studentId);
		if (student == null) {
			// logger.info("Student with studentId = " + studentId + " not found.");
			return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Student>(student, HttpStatus.OK);
	}

	// Create a new student:
	@RequestMapping(value = "/student", method = RequestMethod.POST)
	public ResponseEntity<Void> createStudent(@RequestBody @Valid Student student, UriComponentsBuilder ucBuilder,
			@ModelAttribute("profile") Profile profile) {
		// logger.info("Creating a new student " + student.getName());
		student.setTeacher(teacherService.findById(profile.getTeacherId()));
		student.setLessons(new ArrayList<Lesson>());
		for (Phone phone : student.getPhones()) {
			phone.setStudent(student);
		}
		studentService.save(student);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/student/{studentId}").buildAndExpand(student.getStudentId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// Update a student:
	@RequestMapping(value = "/student/{studentId}", method = RequestMethod.PUT)
	public ResponseEntity<Student> updateStudent(@PathVariable("studentId") int studentId,
			@RequestBody @Valid Student student, @ModelAttribute("profile") Profile profile) {
		// logger.info("Updating student with studentId = " + studentId);
		Student currentStudent = studentService.findById(studentId);
		if (currentStudent == null) {
			// logger.info("Student with studentId = " + studentId + " not found.");
			return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
		}
		currentStudent.setName(student.getName());
		currentStudent.setPhones(student.getPhones());
		for (Phone phone : student.getPhones()) {
			phone.setStudent(currentStudent);
		}
		currentStudent.setNote(student.getNote());
		studentService.update(currentStudent);
		return new ResponseEntity<Student>(currentStudent, HttpStatus.OK);
	}

	// Delete a student:
	@RequestMapping(value = "/student/{studentId}", method = RequestMethod.DELETE)
	public ResponseEntity<Student> deleteStudent(@PathVariable("studentId") int studentId) {
		// logger.info("Deleting student with studentId = " + studentId);
		Student student = studentService.findById(studentId);
		if (student == null) {
			// logger.info("Student with studentId = " + studentId + " not found.");
			return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
		}
		studentService.delete(student);
		return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
	}
}
