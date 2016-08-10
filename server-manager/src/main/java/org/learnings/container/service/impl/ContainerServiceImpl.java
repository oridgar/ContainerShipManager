package org.learnings.container.service.impl;

import java.util.List;

import org.learnings.container.domain.Container;
import org.learnings.container.domain.ContainerImpl;
import org.learnings.container.repository.ContainerRepository;
import org.learnings.container.service.ContainerService;
import org.learnings.system.repository.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContainerServiceImpl implements ContainerService {

	@Autowired
	private ContainerRepository containerRepository;
	
	@Override
	public Container getContainer(String id) {
		return containerRepository.findOneById(id);
	}

	@Override
	public List<ContainerImpl> getContainerList() {
		return containerRepository.findAll();
	}

	@Override
	public void createContainer(ContainerImpl details) {
		//TODO: Send request from MQ to create container
		containerRepository.save(details);
	}

	@Override
	public void deleteContainer(String id) {
		//TODO: Send request from MQ to delete container
		containerRepository.delete(id);
	}

	@Override
	public void startContainer(String id) {
		//TODO: Send request from MQ to start container
	}

	@Override
	public void stopContainer(String id) {
		//TODO: Send request from MQ to stop container
	}

	@Override
	public void restartContainer(String id) {
		//TODO: Send request from MQ to restart container		
	}

}