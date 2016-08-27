package org.learnings.container.domain;

import org.learnings.system.domain.SystemLinux;

public interface Container {
	public int getId();
	public String getName();
	public String getServerId();
	public String getImage();
	public String getUserId();
//	public SystemLinux getServer();
//	void setServer(SystemLinux server);
}
