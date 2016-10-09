package org.learnings.image.controller;

import java.util.List;

import org.learnings.container.domain.Container;
import org.learnings.container.domain.ContainerImpl;
import org.learnings.container.service.ContainerService;
import org.learnings.image.domain.ImageImpl;
import org.learnings.image.service.ImageService;
import org.learnings.libs.ContainerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Controller
public class ImageController {
	@Autowired
	ImageService imageService;
	
	@RequestMapping(value = "/image", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<HttpStatus> createImage(@RequestBody ImageImpl details) {
		imageService.createImage(details);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/image/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ImageImpl getImage(@PathVariable int id) {
		return imageService.getImage(id);
	}

	@RequestMapping(value = "/image", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<ImageImpl> getImageList() {
		return imageService.getImageList();
	}
	
	@RequestMapping(value = "/image/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteImage(@PathVariable int id) {
		imageService.deleteImage(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}