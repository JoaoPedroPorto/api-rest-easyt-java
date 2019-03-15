package com.easyt.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "NOTIFICATION_USER" )
public class NotificationUser implements Serializable {
	private static final long serialVersionUID = -6921391787256945168L;

	@EmbeddedId
	private NotificationUserId id;
	
	@Column(name = "NOTIFICATION_USER_VISUALIZED")
	private boolean visualized;

	// GETTERS AND SETTERS
	
	public NotificationUserId getId() {
		return id;
	}
	public void setId(NotificationUserId id) {
		this.id = id;
	}
	public boolean getVisualized() {
		return visualized;
	}
	public void setVisualized(boolean visualized) {
		this.visualized = visualized;
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
		NotificationUser other = (NotificationUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}