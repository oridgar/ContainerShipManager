package org.learnings.system.domain;

import org.learnings.system.service.SystemType;

public interface System {
	public String getHostName();
	public String getIp();
	SystemType getType();
	String getId();
	void setId(String id);
	String getHostname();
	void setHostname(String hostname);
	void setType(SystemType type);
	void setIp(String ip);
}
