package org.learnings.system.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.learnings.container.domain.Container;
import org.learnings.container.domain.ContainerImpl;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "systems")
//public class SystemLinux implements System {
public class SystemLinux {
	//==========	
	//Attributes
	//==========
	
	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;

	//@Column(name = "type")
	//private SystemType type = SystemType.Linux;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "hostname")
	private String hostname;
	
	@Column(name = "ip")
	private String ip;
	
	@Column(name = "port")
	private String port;
	
	@OneToMany(mappedBy = "server", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<ContainerImpl> containers; 
	
	//=======
	//Methods
	//=======
		
	//@Override
	public String getIp() {
		return ip;
	}
	
	//@Override
	//public SystemType getType() {
		//return type;
	//}

	//@Override
	public int getId() {
		return id;
	}

	//@Override
	public void setId(int id) {
		this.id = id;
	}

	//@Override
	public String getHostname() {
		return hostname;
	}

	//@Override
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	//@Override
	//public void setType(SystemType type) {
		//this.type = type;
	//}

	//@Override
	public void setIp(String ip) {
		this.ip = ip;
	}

	//@Override
	public String getPort() {
		return port;
	}

	//@Override
	public void setPort(String port) {
		this.port = port;
	}

	//@Override
	public String getAddress() {
		return address;
	}

	//@Override
	public void setAddress(String address) {
		this.address = address;
	}

	//@Override
	public String getName() {
		return name;
	}

	//@Override
	public void setName(String name) {
		this.name = name;
	}

	//@Override
	@JsonIgnore
	public List<ContainerImpl> getContainers() {
		return containers;
	}

	//@Override
	public void setContainers(List<ContainerImpl> containers) {
		this.containers = containers;
	}
	
	public void addContainer(ContainerImpl container) {
		this.containers.add(container);
	}

}