package com.mstoyanov.schedule.security;

import java.security.KeyPair;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	private Environment env;
	private AuthenticationManager authenticationManager;
	private static final int ACCESS_TOKEN_EXPIRY = 24 * 60 * 60; // a day
	public static final int REFRESH_TOKEN_EXPIRY = 365 * 60 * 60; // a year
	private static final String REALM = "SCHEDULE-SPRING_BOOT";
	private static final Logger logger = LoggerFactory.getLogger(AuthServerConfig.class);

	@Autowired
	public AuthServerConfig(Environment env, AuthenticationManager authenticationManager) {
		this.env = env;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// @formatter: off
		clients
			.inMemory()
				.withClient(env.getProperty("security.oauth2.client.client-id"))
				.secret(env.getProperty("security.oauth2.client.client-secret"))
				.authorizedGrantTypes("refresh_token", "password")
				.scopes("openid")
				.accessTokenValiditySeconds(ACCESS_TOKEN_EXPIRY)
				.refreshTokenValiditySeconds(REFRESH_TOKEN_EXPIRY);
		// @formatter: on
		logger.info("refresh token expiry = " + REFRESH_TOKEN_EXPIRY);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));
		// @formatter: off
		endpoints
			.authenticationManager(authenticationManager)
			.tokenEnhancer(tokenEnhancerChain)
			.accessTokenConverter(jwtAccessTokenConverter());
		// @formatter: on
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.realm(REALM + "/client");
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("login-oauth2-jwt.jks"),
				"password".toCharArray()).getKeyPair("login-oauth2-jwt");
		converter.setKeyPair(keyPair);
		return converter;
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}
}
