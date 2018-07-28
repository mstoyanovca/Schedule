package com.mstoyanov.schedule.security;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mstoyanov.schedule.domain.Role;
import com.mstoyanov.schedule.domain.UserRepository;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.mstoyanov.schedule.domain.User user = userRepository.findDistinctByEmail(email);
		if (user == null) {
			logger.info("User not found");
			throw new UsernameNotFoundException("User not found");
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return new User(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}
}
