package org.learnings.users.service;

import java.util.List;

import org.learnings.users.domain.User;

public interface UserService {
	public User getUser(String id);
	public User getUserByName(String name);
	public List<User> getUserList();
	public void createUser(User details);
	void deleteUser(String id);
}
