package org.learnings.container.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


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

	@Column(name = "image", length = 100)
	private String image;
	
	//@Column(name = "serverid", length = 100)
	@Transient
	private int serverId;
	
	//@Column(name = "userid", length = 100)
	@Transient
	private String userId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	//@JoinColumn(name="serverid")
	private SystemLinux server;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;	
	
	
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
	public String getImage() {
		return image;
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
	
	
}
