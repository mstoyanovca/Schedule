package com.mstoyanov.schedule.web;

import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.mstoyanov.schedule.domain.Teacher;
import com.mstoyanov.schedule.domain.Token;
import com.mstoyanov.schedule.domain.TokenRepository;
import com.mstoyanov.schedule.domain.User;
import com.mstoyanov.schedule.domain.UserRepository;
import com.mstoyanov.schedule.service.EmailService;

@RestController
public class UserRestController {

	private UserRepository userRepository;
	private EmailService emailService;
	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

	@Autowired
	public UserRestController(UserRepository userRepository, TokenRepository tokenRepository,
			EmailService emailService) {
		this.userRepository = userRepository;
		this.emailService = emailService;
	}

	// find by userId:
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<User> getUser(@PathVariable("userId") long userId) {
		User user = userRepository.findOne(userId);
		if (user == null) {
			logger.info("User with userId = " + userId + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// find by email:
	// (everything after "." gets truncated (file extension), that is why the
	// trailing slash;)
	@RequestMapping(value = "/user/{email}/", method = RequestMethod.HEAD)
	public ResponseEntity<Void> findByEmail(@PathVariable("email") String email) {
		User user = userRepository.findDistinctByEmail(email);
		if (user == null) {
			logger.info("Email = " + email + " not found");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	// find all:
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = (List<User>) userRepository.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	// create:
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/user", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Void> createUser(@RequestBody @Valid User user) {
		if (userRepository.findDistinctByEmail(user.getEmail()) != null) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	// update:
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<Void> updateUser(Authentication authentication, @PathVariable("userId") long userId,
			@RequestBody @Valid User user) {
		User currentUser = null;
		String email = authentication.getName();
		User u = userRepository.findDistinctByEmail(email);
		if (userId == u.getUserId())
			currentUser = u;
		if (currentUser == null) {
			logger.info("User with userId = " + userId + " not found");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(currentUser);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	// generate a password reset token:
	@RequestMapping(value = "/user/reset-password/{email}/", method = RequestMethod.PUT)
	public ResponseEntity<Void> resetPassword(@PathVariable("email") String email) {
		User user = userRepository.findDistinctByEmail(email);
		if (user == null) {
			logger.info("Email = " + email + " not found");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		Token token = new Token();
		token.setToken(UUID.randomUUID().toString().toUpperCase());
		token.setUser(user);
		user.setToken(token);
		// cascades to Token; an old, if present, will be removed, because of
		// 'orphanRemoval = true':
		userRepository.save(user);
		Teacher teacher = user.getTeacher();
		emailService.sendPwdResetToken(email, teacher.getName(), token.getToken());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	// delete one:
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
		User user = userRepository.findOne(id);
		if (user == null) {
			logger.info("User with userId = " + id + " not found");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		userRepository.delete(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	// delete all:
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/user", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAllUsers() {
		logger.info("Deleted all users");
		userRepository.deleteAll();
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
