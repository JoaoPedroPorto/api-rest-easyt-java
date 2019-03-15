package com.easyt.request;

import java.io.Serializable;

import com.easyt.constant.PlatformEnum;

public class DeviceRequest implements Serializable {
	private static final long serialVersionUID = -778111712464725913L;
	
	private Long id;
	private String oldToken;
	private String newToken;
	private PlatformEnum platform;
	
	// GETTERS AND SETTERS
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOldToken() {
		return oldToken;
	}
	public void setOldToken(String oldToken) {
		this.oldToken = oldToken;
	}
	public String getNewToken() {
		return newToken;
	}
	public void setNewToken(String newToken) {
		this.newToken = newToken;
	}
	public PlatformEnum getPlatform() {
		return platform;
	}
	public void setPlatform(PlatformEnum platform) {
		this.platform = platform;
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
		DeviceRequest other = (DeviceRequest) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
