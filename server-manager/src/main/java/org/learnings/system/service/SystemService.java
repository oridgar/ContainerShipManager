package org.learnings.system.service;

import java.util.List;

import org.learnings.libs.Command;
import org.learnings.system.domain.System;
import org.learnings.system.domain.SystemLinux;

public interface SystemService {
	public System getSystem(String id);
	public List<SystemLinux> getSystemList();
	public void createSystem(SystemLinux details);
	void deleteSystem(String id);
	//public void Connect();
	//public void Disconnect();
	public String getHostname();
	String command(System system, Command message);
}
