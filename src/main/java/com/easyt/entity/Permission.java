package com.easyt.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.easyt.constant.StatusEnum;


@Entity
@Table(name="PERMISSION")
public class Permission implements Serializable {
	private static final long serialVersionUID = 4416134043584002712L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PERMISSION_ID")
	private Long id;
	
	@Column(name = "PERMISSION_CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar creationDate;
	
	@Column(name = "PERMISSION_EDITION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar editionDate;
	
	@Column(name = "PERMISSION_DESCRIPTION")
	private String description;
	
	@Column( name = "PERMISSION_MANAGER")
	private boolean manager;
	
	@Column( name = "PERMISSION_MODERATOR")
	private boolean moderator;
	
	@Column( name = "PERMISSION_STUDENT")
	private boolean student;
	
	@Column( name = "PERMISSION_DRIVER")
	private boolean driver;
	
	@Column(name = "PERMISSION_STATUS")
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	
	// GETTERS AND SETTERS

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Calendar getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}
	public Calendar getEditionDate() {
		return editionDate;
	}
	public void setEditionDate(Calendar editionDate) {
		this.editionDate = editionDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean getManager() {
		return manager;
	}
	public void setManager(boolean manager) {
		this.manager = manager;
	}
	public boolean getModerator() {
		return moderator;
	}
	public void setModerator(boolean moderator) {
		this.moderator = moderator;
	}
	public boolean getStudent() {
		return student;
	}
	public void setStudent(boolean student) {
		this.student = student;
	}
	public boolean getDriver() {
		return driver;
	}
	public void setDriver(boolean driver) {
		this.driver = driver;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	
	// HASHCODE
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Permission other = (Permission) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
