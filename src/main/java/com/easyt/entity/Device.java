package com.easyt.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.easyt.constant.PlatformEnum;


@Entity
@Table(name="DEVICE")
public class Device implements Serializable {
	private static final long serialVersionUID = 7247108089877662803L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column( name = "DEVICE_ID" )
	private Long id;
	
    @ManyToOne
	@JoinColumn(name = "DEVICE_FK_USER")
	private User user;
	
	@Column( name = "DEVICE_TOKEN")
	private String token;
	
	@Column( name = "DEVICE_PLATFORM" )
	@Enumerated(EnumType.STRING)
	private PlatformEnum platform;
	
	// GETTERS AND SETTERS

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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
		Device other = (Device) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}