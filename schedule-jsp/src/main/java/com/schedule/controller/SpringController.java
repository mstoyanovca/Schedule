package com.schedule.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.schedule.model.Profile;
import com.schedule.model.Teacher;
import com.schedule.service.TeacherService;

@Controller
@SessionAttributes("profile")
public class SpringController {

	@Autowired
	private TeacherService teacherService;
	// private static final Logger logger = Logger.getLogger(Controller.class);

	// Accessed only after a successful login:
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String profile(ModelMap modelMap, Principal principal) {
		// logger.info("Creating the 'profile' composite session object.");
		Teacher teacher = teacherService.findByEmail(principal.getName());
		if (teacher == null) {
			// logger.info("Access denied.");
			return "redirect:/login?error";
		}
		Profile profile = new Profile();
		profile.setTeacherId(teacher.getTeacherId());
		profile.setTeacherName(teacher.getName());
		modelMap.addAttribute("profile", profile);
		return "redirect:/schedule";
	}

	// Schedule page:
	@RequestMapping(value = "/schedule", method = RequestMethod.GET)
	public String schedule(ModelMap modelMap) {
		// logger.info("Opening the schedule page.");
		return "schedule";
	}

	// Students page:
	@RequestMapping(value = "/students", method = RequestMethod.GET)
	public String students() {
		// logger.info("Opening the students page.");
		return "students";
	}

	// Teacher's account page:
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String account() {
		// logger.info("Opening the teacher's account page.");
		return "account";
	}

	// Change password page:
	@RequestMapping(value = "/change-password", method = RequestMethod.GET)
	public String changePassword() {
		// logger.info("Opening the change password page.");
		return "change-password";
	}
}
