package com.easyt.response;

import java.io.Serializable;

public class NotificationResponse implements Serializable {
	private static final long serialVersionUID = -6978506626156143096L;
	
	private Long id;
	private Long idAssociatedType;
	private String title;
	private String text;
	private String targetAudience;
	
	// GETTERS AND SETTERS
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdAssociatedType() {
		return idAssociatedType;
	}
	public void setIdAssociatedType(Long idAssociatedType) {
		this.idAssociatedType = idAssociatedType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTargetAudience() {
		return targetAudience;
	}
	public void setTargetAudience(String targetAudience) {
		this.targetAudience = targetAudience;
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
		NotificationResponse other = (NotificationResponse) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
