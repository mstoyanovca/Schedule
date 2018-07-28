package com.mstoyanov.schedule.web;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.mstoyanov.schedule.domain.Role;
import com.mstoyanov.schedule.domain.Teacher;
import com.mstoyanov.schedule.domain.TeacherRepository;
import com.mstoyanov.schedule.domain.User;
import com.mstoyanov.schedule.domain.UserRepository;
import com.mstoyanov.schedule.domain.Token;
import com.mstoyanov.schedule.domain.TokenRepository;
import com.mstoyanov.schedule.service.EmailService;

@RestController
public class TeacherRestController {

	private TeacherRepository teacherRepository;
	private UserRepository userRepository;
	private TokenRepository tokenRepository;
	private EmailService emailService;
	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private static final Logger logger = LoggerFactory.getLogger(TeacherRestController.class);

	@Autowired
	public TeacherRestController(TeacherRepository teacherRepository, UserRepository userRepository,
			TokenRepository tokenRepository, EmailService emailService) {
		this.teacherRepository = teacherRepository;
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;
		this.emailService = emailService;
	}

	// find by teacherId:
	@RequestMapping(value = "/teacher/{teacherId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Teacher> getTeacher(Authentication authentication,
			@PathVariable("teacherId") long teacherId) {
		User currentUser = null;
		String email = authentication.getName();
		User u = userRepository.findDistinctByEmail(email);
		if (teacherId == u.getTeacher().getTeacherId())
			currentUser = u;
		if (currentUser == null) {
			logger.info("teacherId = " + teacherId + " not found");
			return new ResponseEntity<Teacher>(HttpStatus.NOT_FOUND);
		}
		currentUser.setPassword("");
		Teacher teacher = currentUser.getTeacher();
		return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
	}

	// create:
	@RequestMapping(value = "/teacher", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Void> createTeacher(@RequestBody @Valid Teacher teacher) {

		User user = teacher.getUser();
		user.setPassword(passwordEncoder.encode(teacher.getUser().getPassword()));
		// a new account has to be activated by email:
		user.setEnabled(false);

		Role role = new Role();
		role.setRole("ROLE_TEACHER");
		role.setUser(user);

		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		user.setRoles(roles);

		// generate an account activation token:
		String uuidToken = UUID.randomUUID().toString().toUpperCase();

		// check for a duplicate token and email:
		if (tokenRepository.findDistinctByToken(uuidToken) == null
				&& userRepository.findDistinctByEmail(user.getEmail()) == null) {

			Token token = new Token();
			token.setToken(uuidToken);
			token.setUser(user);
			user.setToken(token);

			// Cascades to User, Role and Token:
			teacherRepository.save(teacher);

			emailService.sendActivationToken(user.getEmail(), teacher.getName(), token.getToken());

			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} else {
			logger.info("New teacher " + teacher.getName() + " could not be saved");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}

	// update:
	@RequestMapping(value = "/teacher/{teacherId}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<Void> updateTeacher(Authentication authentication, @PathVariable("teacherId") long teacherId,
			@RequestBody @Valid Teacher teacher) {
		User currentUser = null;
		String email = authentication.getName();
		User u = userRepository.findDistinctByEmail(email);
		if (teacherId == u.getTeacher().getTeacherId())
			currentUser = u;
		if (currentUser == null) {
			logger.info("teacherId = " + teacherId + " not found");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		Teacher currentTeacher = currentUser.getTeacher();
		// back-end check, that the email is not already in the DB, or it is the
		// teacher's current email, backing up the AngularJS change-email custom
		// directive:
		if ((userRepository.findDistinctByEmail(teacher.getUser().getEmail()) != null)
				&& !currentTeacher.getUser().getEmail().equals(teacher.getUser().getEmail())) {
			logger.info(teacher.getUser().getEmail() + " is already registered.");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		// persist:
		currentTeacher.setName(teacher.getName());
		currentTeacher.getUser().setEmail(teacher.getUser().getEmail());
		// avoid encryption of the stored (already encrypted) password:
		if (teacher.getUser().getPassword().length() >= 6 && teacher.getUser().getPassword().length() <= 50) {
			// a new password has been typed:
			currentTeacher.getUser().setPassword(passwordEncoder.encode(teacher.getUser().getPassword()));
		}
		teacherRepository.save(currentTeacher);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	// delete:
	@RequestMapping(value = "/teacher/{teacherId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteTeacher(Authentication authentication,
			@PathVariable("teacherId") long teacherId) {
		User currentUser = null;
		String email = authentication.getName();
		User u = userRepository.findDistinctByEmail(email);
		if (teacherId == u.getTeacher().getTeacherId())
			currentUser = u;
		if (currentUser == null) {
			logger.info("teacherId = " + teacherId + " not found");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		teacherRepository.delete(currentUser.getTeacher());
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
