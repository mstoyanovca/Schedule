package com.mstoyanov.schedule.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import com.mstoyanov.schedule.domain.Role;
import com.mstoyanov.schedule.domain.Teacher;
import com.mstoyanov.schedule.domain.Token;
import com.mstoyanov.schedule.domain.TokenRepository;
import com.mstoyanov.schedule.domain.User;
import com.mstoyanov.schedule.domain.UserRepository;
import static com.mstoyanov.schedule.security.AuthServerConfig.REFRESH_TOKEN_EXPIRY;

@RestController
public class LoginController {

	private Environment env;
	private UserRepository userRepository;
	private TokenRepository tokenRepository;
	// private static final String REST_API_URI = "schedule.cfapps.io";
	private static final String AUTH_SERVER_URI = "http://localhost:8080/oauth/token";
	private static final String PASSWORD_GRANT = "?grant_type=password&username=";
	private static final String REFRESH_TOKEN = "?grant_type=refresh_token&refresh_token=";
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	public LoginController(Environment env, UserRepository userRepository, TokenRepository tokenRepository) {
		this.env = env;
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;
	}

	// Login with email and password:
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Void> login(@RequestBody @Valid User user, HttpServletResponse httpResponse) {

		// prepare a request to /oauth/token:
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		// @formatter: off
		String userCredentials = new String(
				Base64.encodeBase64((env.getProperty("security.oauth2.client.client-id") +
				":" + 
				env.getProperty("security.oauth2.client.client-secret")).getBytes()));
		// @formatter: on
		headers.add("Authorization", "Basic " + userCredentials);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		// get a response:
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = null;
		try {
			// @formatter: off
			response = restTemplate.exchange(AUTH_SERVER_URI + 
											 PASSWORD_GRANT + 
											 user.getEmail() + 
											 "&password=" + 
											 user.getPassword(),
											 HttpMethod.POST, request, 
											 Object.class);
			// @formatter: on
		} catch (HttpStatusCodeException exception) {
			if (exception.getStatusCode().value() == 400) {
				logger.info("User not found exception");
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
		}

		// set cookies:
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (HashMap<String, Object>) response.getBody();

		if (map != null) {
			// set an access token cookie:
			Cookie cookie = new Cookie("ACCESS-TOKEN", map.get("access_token").toString());
			// cookie.setSecure(true);
			// cookie.setDomain(REST_API_URI);
			cookie.setMaxAge((int) map.get("expires_in"));
			httpResponse.addCookie(cookie);

			// set a refresh token cookie:
			cookie = new Cookie("REFRESH-TOKEN", map.get("refresh_token").toString());
			cookie.setHttpOnly(true);
			// cookie.setSecure(true);
			// cookie.setDomain(REST_API_URI);
			cookie.setMaxAge(REFRESH_TOKEN_EXPIRY);
			httpResponse.addCookie(cookie);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			logger.info("User not found, map is null");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

	// Refresh an access token:
	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ResponseEntity<Void> refresh(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

		// prepare a request:
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		// @formatter: off
		String userCredentials = new String(
				Base64.encodeBase64((env.getProperty("security.oauth2.client.client-id") + 
				":" +
				env.getProperty("security.oauth2.client.client-secret")).getBytes()));
		// @formatter: on
		headers.add("Authorization", "Basic " + userCredentials);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		// get the refresh token from a cookie:
		String refreshToken = "";
		Cookie[] cookies = httpRequest.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("REFRESH-TOKEN")) {
					refreshToken = cookies[i].getValue();
				}
			}
		}

		// create a response:
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = null;
		try {
			// @formatter: off
			response = restTemplate.exchange(AUTH_SERVER_URI + 
											 REFRESH_TOKEN + 
											 refreshToken, 
											 HttpMethod.POST, 
											 request,
											 Object.class);
			// @formatter: on
		} catch (HttpStatusCodeException exception) {
			if (exception.getStatusCode().value() == 401) {
				logger.info("Refresh token has expired, exception");
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
		}

		// set a cookie with the new access token:
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (HashMap<String, Object>) response.getBody();
		if (map != null) {
			Cookie cookie = new Cookie("ACCESS-TOKEN", map.get("access_token").toString());
			// cookie.setSecure(true);
			// cookie.setDomain(REST_API_URI);
			cookie.setMaxAge((int) map.get("expires_in"));
			httpResponse.addCookie(cookie);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			logger.info("Refresh token has expired, login again, map is null");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

	// Activate an account:
	@RequestMapping(value = "/account/activate/{token}", method = RequestMethod.GET)
	public ResponseEntity<Void> activate(@PathVariable("token") String activationToken) {
		Token token = tokenRepository.findDistinctByToken(activationToken);
		if (token != null) {
			if (token.getExpiryDate().getTime() - Calendar.getInstance().getTime().getTime() > 0) {
				User user = token.getUser();
				if (!user.isEnabled()) {
					user.setEnabled(true);
					user.setToken(null);
					// cascades to Token:
					userRepository.save(user);
					return new ResponseEntity<Void>(HttpStatus.OK);
				} else {
					logger.info("User is enabled already");
					return new ResponseEntity<Void>(HttpStatus.CONFLICT);
				}
			} else {
				logger.info("Token = " + activationToken + " has expired");
				return new ResponseEntity<Void>(HttpStatus.GONE);
			}
		}
		logger.info("Token = " + activationToken + " not found");
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	// Login with a password reset token:
	@RequestMapping(value = "/login/{token}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Teacher> loginWithToken(@PathVariable("token") String pwdResetToken) {
		Token token = tokenRepository.findDistinctByToken(pwdResetToken);
		if (token != null) {
			if (token.getExpiryDate().getTime() - Calendar.getInstance().getTime().getTime() > 0) {
				User user = token.getUser();
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				for (Role role : user.getRoles()) {
					authorities.add(new SimpleGrantedAuthority(role.getRole()));
				}
				Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
						user.getPassword(), authorities);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				if (!user.isEnabled())
					user.setEnabled(true);
				user.setToken(null);
				// cascades to token:
				userRepository.save(user);
				Teacher teacher = user.getTeacher();
				return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
			} else {
				logger.info("Token = " + pwdResetToken + " has expired");
				return new ResponseEntity<Teacher>(HttpStatus.GONE);
			}
		}
		logger.info("Token = " + pwdResetToken + " not found");
		return new ResponseEntity<Teacher>(HttpStatus.NOT_FOUND);
	}
}
