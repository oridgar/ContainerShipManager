package org.learnings.system.controller;

import org.learnings.system.domain.SystemLinux;

import java.util.List;

import org.learnings.Command;
import org.learnings.system.domain.System;
import org.learnings.system.service.SystemService;
import org.learnings.system.service.SystemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SystemController {

	@Autowired
	private SystemService systemService;

	@RequestMapping(value = "/systems/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<System> getSystem(@PathVariable String name) {

		System sys = systemService.getSystem(SystemType.Linux, name);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(sys, httpHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = "/systems", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<SystemLinux>> getSystemList() {
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(systemService.getSystemList(), httpHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = "/systems", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> createSystem(@RequestBody SystemLinux sys) {
		systemService.createSystem(sys);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/systems/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteSystem(@PathVariable String id) {
		systemService.deleteSystem(id);
		return new ResponseEntity<>(HttpStatus.GONE);
	}
	
	@RequestMapping(value = "/systems/hostname", method = RequestMethod.GET)
	public ResponseEntity<HttpStatus> getHostname() {
		systemService.getHostname();
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "/systems/echo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> echo(@RequestBody Command message) {
		String ret = systemService.echo(message);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(ret, httpHeaders, HttpStatus.ACCEPTED);
	}
}
