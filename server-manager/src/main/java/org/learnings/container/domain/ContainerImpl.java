package org.learnings.container.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "container")
public class ContainerImpl implements Container {

	@Id
	@Column(name = "id", length = 100)
	private String id;

	@Column(name = "name", length = 100)
	private String name;

	@Column(name = "image", length = 100)
	private String image;
	
	@Column(name = "serverid", length = 100)
	private String serverId;
	
	@Column(name = "userid", length = 100)
	private String userId;
		
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getServerId() {
		return serverId;
	}

	@Override
	public String getImage() {
		return image;
	}

	@Override
	public String getUserId() {
		return userId;
	}
}
