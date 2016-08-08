package org.learnings.security.login.domain;

import java.util.Collection;
import java.util.List;

import org.learnings.users.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsImpl implements UserDetails {
	
	private User user;
	
	private List<GrantedAuthority> permissions;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (permissions == null) {
			permissions = AuthorityUtils.createAuthorityList(user.getPermission());
		}
		return permissions;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setPermissions(List<GrantedAuthority> permissions) {
		this.permissions = permissions;
	}
	
}
