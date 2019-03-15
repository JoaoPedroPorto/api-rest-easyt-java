package com.easyt.response;

import java.io.Serializable;

public class PermissionResponse implements Serializable {
	private static final long serialVersionUID = 142304223695204459L;
	
	private Long id;
	private String description;
	private String name;
	private Boolean manager;
	private Boolean moderator;
	private Boolean student;
	private Boolean driver;
	private String status;
	
	// GETTERS AND SETTERS
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getManager() {
		return manager;
	}
	public void setManager(Boolean manager) {
		this.manager = manager;
	}
	public Boolean getModerator() {
		return moderator;
	}
	public void setModerator(Boolean moderator) {
		this.moderator = moderator;
	}
	public Boolean getStudent() {
		return student;
	}
	public void setStudent(Boolean student) {
		this.student = student;
	}
	public Boolean getDriver() {
		return driver;
	}
	public void setDriver(Boolean driver) {
		this.driver = driver;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
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
		PermissionResponse other = (PermissionResponse) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
