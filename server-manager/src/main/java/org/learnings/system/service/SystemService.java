package org.learnings.system.service;

import java.util.List;

import org.learnings.libs.Command;
import org.learnings.libs.ICommand;
import org.learnings.system.domain.System;
import org.learnings.system.domain.SystemLinux;

public interface SystemService {
	public SystemLinux getSystem(int id);
	public List<SystemLinux> getSystemList();
	public void createSystem(SystemLinux details);
	//public void Connect();
	//public void Disconnect();
	public String getHostname();
	String command(SystemLinux system, Command message);
	public void deleteSystem(int id);
	String sendCommand(ICommand command);
}
