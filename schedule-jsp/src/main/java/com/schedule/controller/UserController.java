package com.schedule.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.schedule.model.Profile;
import com.schedule.model.Teacher;
import com.schedule.model.User;
import com.schedule.service.UserService;

@Controller
@SessionAttributes("profile")
public class UserController {

	@Autowired
	private UserService userService;
	// private static final Logger logger =
	// Logger.getLogger(UserController.class);

	// Activate a new account:
	@RequestMapping(value = "/activate/{token}/", method = RequestMethod.GET)
	public String activate(@PathVariable("token") String activationToken) {
		// logger.info("Logging in by email confirmation token " +
		// activationToken);
		User user = userService.findByToken(activationToken);
		if (user == null
				|| (user.getToken().getExpiryDate().getTime() - Calendar.getInstance().getTime().getTime() <= 0)) {
			return "redirect:/login?error";
		}
		user.setEnabled(true);
		user.setToken(null);
		userService.update(user);
		return "redirect:/login";
	}

	// Login with a password reset token:
	@RequestMapping(value = "/reset-password/{token}/", method = RequestMethod.GET)
	public String changePassword(@PathVariable("token") String pwdResetToken, ModelMap modelMap) {
		// logger.info("Logging in by password reset token " + pwdResetToken);
		User user = userService.findByToken(pwdResetToken);
		if (user == null
				|| (user.getToken().getExpiryDate().getTime() - Calendar.getInstance().getTime().getTime() <= 0)) {
			// logger.info("Invalid token.");
			return "redirect:/login?error";
		}
		// Erase the password reset token:
		user.setToken(null);
		userService.update(user);
		// Authenticate the user; limit access to the change-password.jsp and
		// the updateTeacher() method only:
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_CHANGE_PASSWORD"));
		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(),
				authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// Add the logged in teacher's name to the session:
		Teacher teacher = user.getTeacher();
		Profile profile = new Profile();
		profile.setTeacherId(teacher.getTeacherId());
		profile.setTeacherName(teacher.getName());
		modelMap.addAttribute("profile", profile);
		return "redirect:/change-password";
	}
}
