package org.learnings.system.domain;

import java.util.Set;

import org.learnings.container.domain.Container;
import org.learnings.container.domain.ContainerImpl;
import org.learnings.system.service.SystemType;

public interface System {
	public String getIp();
	//SystemType getType();
	public int getId();
	public void setId(int id);
	public String getHostname();
	public void setHostname(String hostname);
	//void setType(SystemType type);
	public void setIp(String ip);
	String getPort();
	void setPort(String port);
	String getAddress();
	void setAddress(String address);
	String getName();
	void setName(String name);
	
	//Set<ContainerImpl> getContainers();
	//void setContainers(Set<ContainerImpl> containers);
}
