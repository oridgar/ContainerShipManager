package org.learnings.system.domain;

import org.learnings.system.service.SystemType;

public interface System {
	public String getIp();
	//SystemType getType();
	public String getId();
	public void setId(String id);
	public String getHostname();
	public void setHostname(String hostname);
	//void setType(SystemType type);
	public void setIp(String ip);
}
