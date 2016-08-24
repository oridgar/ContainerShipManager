package org.learnings.system.controller;

import org.learnings.system.domain.SystemLinux;

import java.util.List;

import org.learnings.libs.Command;
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
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Controller
public class SystemController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SystemService systemService;

	@RequestMapping(value = "/server/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<System> getSystem(@PathVariable String name) {

		System sys = systemService.getSystem(name);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(sys, httpHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = "/server", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<SystemLinux>> getSystemList() {
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(systemService.getSystemList(), httpHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = "/server", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> createSystem(@RequestBody SystemLinux sys) {
		logger.info("Create new system " + sys.getHostname());
		systemService.createSystem(sys);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/server/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteSystem(@PathVariable String id) {
		logger.info("Delete system with id " + id);
		systemService.deleteSystem(id);
		return new ResponseEntity<>(HttpStatus.GONE);
	}
	
	@RequestMapping(value = "/server/{id}", method = RequestMethod.GET)
	public ResponseEntity<System> getHostname(@PathVariable String id) {
		System sys = systemService.getSystem(id);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(sys, httpHeaders, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "/server/{id}/command", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> echo(@RequestBody Command message,@PathVariable String id) {
		org.learnings.system.domain.System server = systemService.getSystem(id);
		String ret = systemService.command(server, message);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(ret, httpHeaders, HttpStatus.ACCEPTED);
	}
}
