package org.learnings.container.service;

import java.util.List;

import org.learnings.container.domain.Container;
import org.learnings.container.domain.ContainerImpl;

public interface ContainerService {
	public ContainerImpl getContainer(int id);
	public List<ContainerImpl> getContainerList();
	public void createContainer(ContainerImpl details);
	public void deleteContainer(int id);
	
	public void startContainer(int id);
	public void stopContainer(int id);
	public void restartContainer(int id);
}
