package com.schedule.controller;

import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import com.schedule.email.VelocityEmailServiceImpl;
import com.schedule.model.Profile;
import com.schedule.model.Role;
import com.schedule.model.Teacher;
import com.schedule.model.Token;
import com.schedule.model.User;
import com.schedule.service.PasswordService;
import com.schedule.service.TeacherService;
import com.schedule.service.UserService;

@RestController
@SessionAttributes("profile")
public class TeacherRESTController {

	@Autowired
	private UserService userService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private PasswordService passwordService;
	@Autowired
	private VelocityEmailServiceImpl emailService;
	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	// private static final Logger logger =
	// Logger.getLogger(TeacherRESTController.class);

	// Find a Teacher by teacherId:
	@RequestMapping(value = "/teacher/{teacherId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Teacher> findTeacher(@PathVariable("teacherId") int teacherId) {
		// logger.info("Find a Teacher with teacherId = " + teacherId);
		Teacher teacher = teacherService.findById(teacherId);
		if (teacher == null) {
			// logger.info("Teacher with teacherId = " + teacherId + " not
			// found.");
			return new ResponseEntity<Teacher>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
	}

	// Save a new teacher:
	@RequestMapping(value = "/teacher", method = RequestMethod.POST)
	public ResponseEntity<Void> createTeacher(@RequestBody @Valid Teacher teacher, UriComponentsBuilder ucBuilder) {

		// logger.info("Creating a new teacher " + teacher.getName());

		Role role = new Role();
		role.setRole("ROLE_TEACHER");
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);

		User user = teacher.getUser();
		// new account has to be activated by email:
		user.setEnabled(false);
		user.setRoles(roles);
		role.setUser(user);

		// generate an email verification token:
		Token token = new Token();
		token.setToken(passwordService.generate(15, 20, 1, 1, 1, 1).toString());
		token.setUser(user);
		user.setToken(token);

		user.setTeacher(teacher);

		// encode the password:
		teacher.getUser().setPassword(passwordEncoder.encode(teacher.getUser().getPassword()));

		// Cascades to User, Role and Token:
		teacherService.save(teacher);
		// logger.info("Saved a new teacher " + teacher.getName());

		// send an email:
		emailService.sendEmailConfirmationToken(user);
		// logger.info("Sent email verification token to " + user.getEmail());

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/teacher/{id}").buildAndExpand(teacher.getTeacherId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// Update a Teacher:
	@RequestMapping(value = "/teacher/{teacherId}", method = RequestMethod.PUT)
	public ResponseEntity<Teacher> updateTeacher(@PathVariable("teacherId") int teacherId,
			@RequestBody @Valid Teacher teacher, @ModelAttribute("profile") Profile profile, ModelMap modelMap) {
		// logger.info("Updating teacher with teacherId = " + teacherId);
		Teacher currentTeacher = teacherService.findById(teacherId);
		if (currentTeacher == null) {
			// logger.info("A teacher with teacherId = " + teacherId + "not
			// found.");
			return new ResponseEntity<Teacher>(HttpStatus.NOT_FOUND);
		}
		// back-end check, that the email is not already in the DB, or it is
		// the teacher's current email, backing up the AngularJS change-password
		// custom directive:
		if ((userService.findByEmail(teacher.getUser().getEmail()) != null)
				&& !currentTeacher.getUser().getEmail().equals(teacher.getUser().getEmail())) {
			// logger.info("Email " + teacher.getUser().getEmail() + " is
			// already registered.");
			return new ResponseEntity<Teacher>(HttpStatus.NOT_FOUND);
		}
		// persist:
		currentTeacher.setName(teacher.getName());
		currentTeacher.getUser().setEmail(teacher.getUser().getEmail());
		// avoid encryption of the stored (already encrypted) password:
		if (teacher.getUser().getPassword().length() <= 20) {
			// a new password has been typed:
			currentTeacher.getUser().setPassword(passwordEncoder.encode(teacher.getUser().getPassword()));
		}
		teacherService.update(currentTeacher);
		// Update session data:
		profile.setTeacherName(currentTeacher.getName());
		modelMap.addAttribute("profile", profile);
		return new ResponseEntity<Teacher>(currentTeacher, HttpStatus.OK);
	}

	// Delete a Teacher:
	@RequestMapping(value = "/teacher/{teacherId}", method = RequestMethod.DELETE)
	public ResponseEntity<Teacher> deleteTeacher(@PathVariable("teacherId") int teacherId) {
		// logger.info("Deleting teacherId " + teacherId);
		Teacher teacher = teacherService.findById(teacherId);
		if (teacher == null) {
			// logger.info("Teacher with teacherId = " + teacherId + "not
			// found.");
			return new ResponseEntity<Teacher>(HttpStatus.NOT_FOUND);
		}
		teacherService.delete(teacher);
		return new ResponseEntity<Teacher>(HttpStatus.NO_CONTENT);
	}
}
