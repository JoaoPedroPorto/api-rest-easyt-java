package com.easyt.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.easyt.constant.StatusEnum;

@Entity
@Table( name = "USER_PERMISSION" )
public class UserPermission implements Serializable {
	private static final long serialVersionUID = -4870820993246738330L;

	@EmbeddedId
	private UserPermissionId id;
	
	@Column(name = "USER_PERMISSION_STATUS")
	@Enumerated(EnumType.STRING)
	private StatusEnum status;

	// GETTERS AND SETTERS
	
	public UserPermissionId getId() {
		return id;
	}
	public void setId(UserPermissionId id) {
		this.id = id;
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
		UserPermission other = (UserPermission) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}