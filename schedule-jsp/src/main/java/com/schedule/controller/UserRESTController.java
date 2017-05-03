package com.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.schedule.email.VelocityEmailServiceImpl;
import com.schedule.model.Token;
import com.schedule.model.User;
import com.schedule.service.PasswordService;
import com.schedule.service.UserService;

@RestController
public class UserRESTController {

	@Autowired
	UserService userService;
	@Autowired
	PasswordService passwordService;
	@Autowired
	private VelocityEmailServiceImpl emailService;
	// private static final Logger logger = Logger.getLogger(UserRESTController.class);

	/*
	 * everything after "." gets truncated (file extension), that is why the
	 * trailing slash.
	 */
	// Check if this email is registered:
	@RequestMapping(value = "/user/{email}/", method = RequestMethod.HEAD)
	public ResponseEntity<User> findByEmail(@PathVariable("email") String email) {
		// logger.info("Find a user by email = " + email);
		User user = userService.findByEmail(email);
		if (user == null) {
			// logger.info("User with email = " + email + " not found.");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(HttpStatus.OK);
	}

	// Generate a password reset token:
	@RequestMapping(value = "/user/{email}/", method = RequestMethod.PUT)
	public ResponseEntity<User> update(@PathVariable("email") String email) {
		// logger.info("Generating password reset token for " + email);
		User user = userService.findByEmail(email);
		if (user == null) {
			// logger.info("user with email = " + email + " not found.");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		// generate a password reset token:
		Token token = new Token();
		token.setToken(passwordService.generate(15, 20, 1, 1, 1, 1).toString());
		token.setUser(user);
		user.setToken(token);
		userService.update(user);
		emailService.sendPasswordResetToken(user);
		return new ResponseEntity<User>(HttpStatus.OK);
	}
}
