package org.learnings.image.domain;

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
@Table(name = "image")
public class ImageImpl {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private int id;

	@Column(name = "name", length = 100)
	private String name;


	//@Override
	public int getId() {
		return id;
	}

	//@Override
	public String getName() {
		return name;
	}
}
