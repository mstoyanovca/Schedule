package com.schedule.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.schedule.model.User;

@Controller
public class LoginController {

	// private static final Logger logger = Logger.getLogger(LoginController.class);

	// Home page:
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		// logger.info("Requested the home page.");
		return "redirect:/home";
	}

	// Login page:
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap modelMap) {
		// logger.info("Opening the login page.");
		modelMap.addAttribute("user", new User());
		return "login";
	}

	// Access denied page:
	@RequestMapping(value = "/access_denied", method = RequestMethod.GET)
	public String accessDenied() {
		// logger.info("Access denied.");
		return "redirect:/login?error";
	}

	// Logout page:
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		// logger.info("Logout page.");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}
}
