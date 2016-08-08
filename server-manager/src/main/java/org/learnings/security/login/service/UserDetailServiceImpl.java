package org.learnings.security.login.service;

import org.learnings.security.login.domain.UserDetailsImpl;
import org.learnings.users.domain.User;
import org.learnings.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User currentUser = userService.getUserByName(username);
		UserDetailsImpl userDetails = new UserDetailsImpl();
		userDetails.setUser(currentUser);
		return userDetails;
	}
}
