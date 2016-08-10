package org.learnings.container.service;

import java.util.List;

import org.learnings.container.domain.Container;
import org.learnings.container.domain.ContainerImpl;

public interface ContainerService {
	public Container getContainer(String id);
	public List<ContainerImpl> getContainerList();
	public void createContainer(ContainerImpl details);
	public void deleteContainer(String id);
	
	public void startContainer(String id);
	public void stopContainer(String id);
	public void restartContainer(String id);
}
