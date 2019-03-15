package com.easyt.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class UserPermissionId implements Serializable {
	private static final long serialVersionUID = -8884998529741847263L;

	@ManyToOne
	@JoinColumn(name = "USER_PERMISSION_FK_USER")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "USER_PERMISSION_FK_PERMISSION")
	private Permission permission;
	
	// GETTERS AND SETTERS

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	
	// HASHCODE
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((permission == null) ? 0 : permission.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		UserPermissionId other = (UserPermissionId) obj;
		if (permission == null) {
			if (other.permission != null)
				return false;
		} else if (!permission.equals(other.permission))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
}