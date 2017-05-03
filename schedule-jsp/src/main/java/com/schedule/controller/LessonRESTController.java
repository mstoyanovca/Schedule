package com.schedule.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import com.schedule.model.Profile;
import com.schedule.service.LessonService;
import com.schedule.service.TeacherService;

@RestController
@SessionAttributes("profile")
public class LessonRESTController {

	@Autowired
	private TeacherService teacherService;
	@Autowired
	private LessonService lessonService;
	// private static final Logger logger = Logger.getLogger(LessonRESTController.class);

	// Find all lessons:
	@RequestMapping(value = "/lesson", method = RequestMethod.GET)
	public ResponseEntity<List<Lesson>> listAllLessons(@ModelAttribute("profile") Profile profile) {
		// logger.info("Find all lessons.");
		List<Lesson> lessons = lessonService.findAllByTeacherId(profile.getTeacherId());
		if (lessons.isEmpty()) {
			return new ResponseEntity<List<Lesson>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Lesson>>(lessons, HttpStatus.OK);
	}

	// Save a new lesson:
	@RequestMapping(value = "/lesson", method = RequestMethod.POST)
	public ResponseEntity<Void> createLesson(@RequestBody @Valid Lesson lesson, UriComponentsBuilder ucBuilder,
			@ModelAttribute("profile") Profile profile) {
		// logger.info("Creating a new lesson.");
		lesson.setTeacher(teacherService.findById(profile.getTeacherId()));
		lessonService.save(lesson);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/lesson/{lessonId}").buildAndExpand(lesson.getLessonId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// Update a lesson:
	@RequestMapping(value = "/lesson/{lessonId}", method = RequestMethod.PUT)
	public ResponseEntity<Lesson> updateLesson(@PathVariable("lessonId") int lessonId,
			@RequestBody @Valid Lesson lesson, @ModelAttribute("profile") Profile profile) {
		// logger.info("Updating lesson with lessonId = " + lessonId);
		Lesson currentLesson = lessonService.findById(lessonId);
		if (currentLesson == null) {
			// logger.info("Lesson with lessonId = " + lessonId + " not found.");
			return new ResponseEntity<Lesson>(HttpStatus.NOT_FOUND);
		}
		lesson.setTeacher(teacherService.findById(profile.getTeacherId()));
		lessonService.update(lesson);
		return new ResponseEntity<Lesson>(lesson, HttpStatus.OK);
	}

	// Delete a lesson:
	@RequestMapping(value = "/lesson/{lessonId}", method = RequestMethod.DELETE)
	public ResponseEntity<Lesson> deleteLesson(@PathVariable("lessonId") int lessonId) {
		// logger.info("Deleting lesson with lessonId = " + lessonId);
		Lesson lesson = lessonService.findById(lessonId);
		if (lesson == null) {
			// logger.info("Lesson with lessonId = " + lessonId + " not found.");
			return new ResponseEntity<Lesson>(HttpStatus.NOT_FOUND);
		}
		lessonService.delete(lesson);
		return new ResponseEntity<Lesson>(HttpStatus.NO_CONTENT);
	}
}
