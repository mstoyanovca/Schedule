package com.mstoyanov.schedule.security;

import static com.mstoyanov.schedule.security.AuthServerConfig.REFRESH_TOKEN_EXPIRY;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CompositeFilter;
import com.mstoyanov.schedule.domain.User;
import com.mstoyanov.schedule.domain.UserRepository;

@Order(6)
@RestController
@EnableOAuth2Client
@Configuration
public class SocialSSOConfig extends WebSecurityConfigurerAdapter {

	private Environment env;
	private UserRepository userRepository;
	private OAuth2ClientContext oauth2ClientContext;
	private static final String AUTH_SERVER_URI = "http://localhost:8080/oauth/token";
	private static final String PASSWORD_GRANT = "?grant_type=password&username=";
	private static final Logger logger = LoggerFactory.getLogger(SocialSSOConfig.class);

	@Autowired
	public SocialSSOConfig(OAuth2ClientContext oauth2ClientContext, UserRepository userRepository, Environment env) {
		this.oauth2ClientContext = oauth2ClientContext;
		this.userRepository = userRepository;
		this.env = env;
	}

	@RequestMapping("/sso/user")
	@SuppressWarnings("unchecked")
	public Map<String, String> user(Principal principal) {
		User user = null;
		if (principal != null) {
			// the user is logged in into social SSO:
			OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
			Authentication authentication = oAuth2Authentication.getUserAuthentication();
			Map<String, String> details = new LinkedHashMap<>();
			details = (Map<String, String>) authentication.getDetails();
			// logger.info("details = " + details);
			// "email" from FB, "emailAddress" from LI:
			String email = details.get("email") != null ? details.get("email") : details.get("emailAddress");
			user = userRepository.findDistinctByEmail(email);
			Map<String, String> map = new LinkedHashMap<>();
			if (user != null) {
				// the email of the social SSO user is registered in the DB:
				map.put("id", principal.getName());
				return map;
			}
		}
		return null;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter: off
		http
			.antMatcher("/**")
			.authorizeRequests()
				// home page: 
				.antMatchers("/").permitAll()
				// static resources:
				.antMatchers("/views/**", "/css/**", "/js/**", "/images/**").permitAll()
				// login:
				.antMatchers("/login/**", "/login/{token}", "/refresh", "/sso/user").permitAll()
				// queries:
				.antMatchers("/user/{email}/", "/user/reset-password/{email}/", "/teacher/").permitAll()
				// security:
				.antMatchers("/oauth/token", "/account/activate/{token}", "/activate/{token}").permitAll()
			.anyRequest()
				.authenticated().and()
			.sessionManagement()
				.disable()
			.logout()
				.logoutSuccessUrl("/")
				.permitAll()
				.deleteCookies("ACCESS-TOKEN", "REFRESH-TOKEN").and()
			.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
			.exceptionHandling()
			      .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and()
			.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
		// @formatter: on
	}

	private Filter ssoFilter() {
		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();
		filters.add(ssoFilter(facebook(), "/login/facebook"));
		filters.add(ssoFilter(linkedin(), "/login/linkedin"));
		filter.setFilters(filters);
		return filter;
	}

	private Filter ssoFilter(ClientResources client, String path) {
		OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
		OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
		filter.setRestTemplate(template);
		filter.setTokenServices(
				new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId()));
		return filter;
	}

	class ClientResources {

		@NestedConfigurationProperty
		private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();
		@NestedConfigurationProperty
		private ResourceServerProperties resource = new ResourceServerProperties();

		public AuthorizationCodeResourceDetails getClient() {
			return client;
		}

		public ResourceServerProperties getResource() {
			return resource;
		}
	}

	@Bean
	@ConfigurationProperties("linkedin")
	public ClientResources linkedin() {
		return new ClientResources();
	}

	@Bean
	@ConfigurationProperties("facebook")
	public ClientResources facebook() {
		return new ClientResources();
	}

	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}

	// Login with email after successful social SSO:
	@Secured("ROLE_USER")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ssologin", method = RequestMethod.GET)
	public ResponseEntity<Void> login(Principal principal, HttpServletResponse httpResponse) {

		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
		Authentication authentication = oAuth2Authentication.getUserAuthentication();
		Map<String, String> details = new LinkedHashMap<>();
		details = (Map<String, String>) authentication.getDetails();
		// "email" from FB, "emailAddress" from LI:
		String email = details.get("email") != null ? details.get("email") : details.get("emailAddress");
		User user = userRepository.findDistinctByEmail(email);

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
		Map<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();

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
}
