package org.learnings.users.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.learnings.users.domain.User;
import org.learnings.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Value("${management.admin.login}")
	private String adminLogin;
		
	@Value("${management.admin.password}")
	private String adminPassword;
	
	@Override
	public User getUser(String id) {
		return userRepository.findOneById(id);
	}

	@Override
	public List<User> getUserList() {
		return userRepository.findAll();
	}

	@Override
	public void createUser(User details) {
		userRepository.save(details);
	}

	@Override
	public void deleteUser(String id) {
		userRepository.delete(id);
	}
	
	@PostConstruct
	public void init() {
		User existingAdmin = userRepository.findOneByName(adminLogin);
		if (existingAdmin == null) {
			User newAdmin = new User();
			newAdmin.setId("0");
			newAdmin.setName(adminLogin);
			//newAdmin.setEncodedPassword(adminPassword);
			newAdmin.setPassword(adminPassword);
			newAdmin.setPermission("Admin");
			userRepository.save(newAdmin);
		}
	}

	@Override
	public User getUserByName(String name) {
		return userRepository.findOneByName(name);
	}
}
