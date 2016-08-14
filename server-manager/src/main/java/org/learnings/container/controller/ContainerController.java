package org.learnings.container.controller;

import java.util.List;

import org.learnings.container.domain.Container;
import org.learnings.container.domain.ContainerImpl;
import org.learnings.container.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Controller
public class ContainerController {
	@Autowired
	ContainerService containerService;
	
	@RequestMapping(value = "/container", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<HttpStatus> createContainer(@RequestBody ContainerImpl details) {
		containerService.createContainer(details);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/container/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<HttpStatus> createContainer(@PathVariable String id, @RequestParam String action) {
		switch (action) {
		case "start":
			containerService.startContainer(id);			
			break;
		case "stop":
			containerService.stopContainer(id);
			break;
		case "restart":
			containerService.restartContainer(id);
			break;
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/container/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Container getContainer(@PathVariable String id) {
		return containerService.getContainer(id);
	}

	@RequestMapping(value = "/container", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<ContainerImpl> getContainerList() {
		return containerService.getContainerList();
	}
	
	@RequestMapping(value = "/container/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteContainer(@PathVariable String id) {
		containerService.deleteContainer(id);
		return new ResponseEntity<>(HttpStatus.GONE);
	}
}