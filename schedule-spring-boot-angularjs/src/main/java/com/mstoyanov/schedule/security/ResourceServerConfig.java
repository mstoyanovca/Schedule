package com.mstoyanov.schedule.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "login-oauth2-jwt";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter: off
		http.
			anonymous().disable()
			.requestMatchers().antMatchers("/student/**", "/lesson/**", "/teacher/{id}").and()
			.authorizeRequests().antMatchers("/student/**", "/lesson/**", "/teacher/{id}")
				.access("hasRole('TEACHER') or hasRole('ADMIN')");
		// @formatter: on
	}
}
