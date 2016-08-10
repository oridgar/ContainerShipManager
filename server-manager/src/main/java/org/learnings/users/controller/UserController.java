package org.learnings.users.controller;

import java.util.List;

import org.learnings.system.service.SystemService;
import org.learnings.users.domain.User;
import org.learnings.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasAuthority('Admin')")
	@RequestMapping(value = "/user/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public User getUser(@PathVariable String name) {
		return userService.getUserByName(name);
	}
	
	@PreAuthorize("hasAuthority('Admin')")
	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<User> getUsersList() {
		return userService.getUserList();
	}
	
	@PreAuthorize("hasAuthority('Admin')")
	@RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<HttpStatus> getUsersList(@RequestBody User user) {
		userService.createUser(user);		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
