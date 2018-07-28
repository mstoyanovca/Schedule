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
import com.mstoyanov.schedule.domain.Lesson;
import com.mstoyanov.schedule.domain.LessonRepository;
import com.mstoyanov.schedule.domain.Teacher;
import com.mstoyanov.schedule.domain.TeacherRepository;
import com.mstoyanov.schedule.domain.User;
import com.mstoyanov.schedule.domain.UserRepository;

@RestController
public class LessonRestController {

	private LessonRepository lessonRepository;
	private UserRepository userRepository;
	private TeacherRepository teacherRepository;
	private static final Logger logger = LoggerFactory.getLogger(LessonRestController.class);

	@Autowired
	public LessonRestController(LessonRepository lessonRepository, UserRepository userRepository,
			TeacherRepository teacherRepository) {
		this.lessonRepository = lessonRepository;
		this.userRepository = userRepository;
		this.teacherRepository = teacherRepository;
	}

	// find all:
	@RequestMapping(value = "/lesson", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Lesson>> findLessons(Authentication authentication) {
		String email = authentication.getName();
		User user = userRepository.findDistinctByEmail(email);
		Teacher teacher = user.getTeacher();
		List<Lesson> lessons = teacher.getLessons();
		if (lessons.isEmpty()) {
			logger.info("Lessons list is empty");
			return new ResponseEntity<List<Lesson>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Lesson>>(lessons, HttpStatus.OK);
	}

	// create:
	@RequestMapping(value = "/lesson", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Void> createLesson(Authentication authentication, @RequestBody @Valid Lesson lesson) {
		String email = authentication.getName();
		User user = userRepository.findDistinctByEmail(email);
		Teacher teacher = user.getTeacher();
		lesson.setTeacher(teacher);
		lessonRepository.save(lesson);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	// update:
	@RequestMapping(value = "/lesson/{lessonId}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<Void> updateLesson(Authentication authentication, @PathVariable("lessonId") long lessonId,
			@RequestBody @Valid Lesson lesson) {
		Lesson currentLesson = null;
		String email = authentication.getName();
		User user = userRepository.findDistinctByEmail(email);
		Teacher teacher = user.getTeacher();
		List<Lesson> lessons = teacher.getLessons();
		for (Lesson l : lessons) {
			if (lessonId == l.getLessonId())
				currentLesson = l;
		}
		if (currentLesson == null) {
			logger.info("lessonId = " + lessonId + " not found.");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		currentLesson.setDow(lesson.getDow());
		currentLesson.setStartTime(lesson.getStartTime());
		currentLesson.setEndTime(lesson.getEndTime());
		currentLesson.setStudent(lesson.getStudent());
		currentLesson.setNote(lesson.getNote());
		currentLesson.setTeacher(teacher);
		lessonRepository.save(lesson);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	// delete:
	@RequestMapping(value = "/lesson/{lessonId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteLesson(Authentication authentication, @PathVariable("lessonId") long lessonId) {
		Lesson lesson = null;
		String email = authentication.getName();
		User user = userRepository.findDistinctByEmail(email);
		Teacher teacher = user.getTeacher();
		List<Lesson> lessons = teacher.getLessons();
		for (Lesson l : lessons) {
			if (lessonId == l.getLessonId())
				lesson = l;
		}
		if (lesson == null) {
			logger.info("lessonId = " + lessonId + " not found.");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		lessons.remove(lesson);
		teacherRepository.save(teacher);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
