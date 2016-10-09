package org.learnings.container.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.learnings.image.domain.ImageImpl;
import org.learnings.system.domain.System;
import org.learnings.system.domain.SystemLinux;
import org.learnings.users.domain.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "container")
public class ContainerImpl {
//public class ContainerImpl implements Container {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;

	@Column(name = "name", length = 100)
	private String name;

	//@Column(name = "image", length = 100)
	//private String image;

	@Column(name = "ip")
	private String ip;
	
	@Column(name = "netmask")
	private String netmask;
	
	@Column(name = "gateway")
	private String gateway;
	
	//@Column(name = "serverid", length = 100)
	@Transient
	private int serverId;

	//@Column(name = "userid", length = 100)
	@Transient
	private String userId;

	//@Column(name = "image_id")
	@Transient
	private int imageId;
	
	@Transient
	private String status;

	//----
	//Join
	//----
	
	@ManyToOne(fetch = FetchType.EAGER)
	//@JoinColumn(name="serverid")
	private SystemLinux server;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;	

	@ManyToOne(fetch = FetchType.EAGER)
	public ImageImpl image;

	//-------------------
	//Getters and Setters
	//-------------------
	
	//@Override
	public int getId() {
		return id;
	}

	//@Override
	public String getName() {
		return name;
	}

	//@Override
	public int getServerId() {
		return serverId;
	}

	//@Override
	public ImageImpl getImage() {
		return image;
	}
	
	public void setImage(ImageImpl image) {
		this.image = image;
	}

	
	public int getImageId() {
		return imageId;
	}
	
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	
	//@Override
	public String getUserId() {
		return userId;
	}

//	@Override
	public SystemLinux getServer() {
		return server;
	}
//
//	@Override
	public void setServer(SystemLinux server) {
		this.server = server;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNetmask() {
		return netmask;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}