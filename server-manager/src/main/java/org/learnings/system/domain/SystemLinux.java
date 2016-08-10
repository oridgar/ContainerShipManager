package org.learnings.system.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.learnings.system.service.SystemType;

@Entity
@Table(name = "systems")
public class SystemLinux implements System {
	
	@Id
	@Column(name = "id", length = 100)
	private String id;

	//@Column(name = "type")
	//private SystemType type = SystemType.Linux;
	
	@Column(name = "hostname")
	private String hostname;
	
	@Column(name = "ip")
	private String ip;
	
	@Override
	public String getIp() {
		return ip;
	}
	
	//@Override
	//public SystemType getType() {
		//return type;
	//}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getHostname() {
		return hostname;
	}

	@Override
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	//@Override
	//public void setType(SystemType type) {
		//this.type = type;
	//}

	@Override
	public void setIp(String ip) {
		this.ip = ip;
	}
}