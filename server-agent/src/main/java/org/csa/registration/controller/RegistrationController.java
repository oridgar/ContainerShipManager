package org.csa.registration.controller;

import org.csa.registration.service.RegistrationService;
import org.learnings.libs.RegisterCsaDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class RegistrationController {
	
	
	@Autowired
	private RegistrationService registrationService;
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegisterCsaDO> register(@RequestBody RegisterCsaDO registrationDO) {
		
		registrationService.register(registrationDO);
		return new ResponseEntity<>(registrationDO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/unregister", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> register() {
		
		registrationService.unregister();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	
}
