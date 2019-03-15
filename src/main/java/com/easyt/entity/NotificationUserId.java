package com.easyt.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class NotificationUserId implements Serializable {
	private static final long serialVersionUID = 2875914665256807254L;

	@ManyToOne
	@JoinColumn(name = "NOTIFICATION_USER_FK_USER")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "NOTIFICATION_USER_FK_NOTIFICATION")
	private Notification notification;

	// GETTERS AND SETTERS
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Notification getNotification() {
		return notification;
	}
	public void setNotification(Notification notification) {
		this.notification = notification;
	}
	
	// HASHCODE
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((notification == null) ? 0 : notification.hashCode());
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
		NotificationUserId other = (NotificationUserId) obj;
		if (notification == null) {
			if (other.notification != null)
				return false;
		} else if (!notification.equals(other.notification))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
	
	
	
}