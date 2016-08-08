package org.learnings.users.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@Column(name = "id", length = 100)
	private String id;
	
	@Column (name = "name", length = 100)
	private String name;
	
	@Column (name = "password", length = 100)
	private String password;

	@Column (name = "permission", length = 100)
	private String permission;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setEncodedPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
}