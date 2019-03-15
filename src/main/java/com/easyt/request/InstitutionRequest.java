package com.easyt.request;

import java.io.Serializable;
import java.util.Calendar;

public class InstitutionRequest implements Serializable {
	private static final long serialVersionUID = 1821124826635179825L;
	
	private Long id;
	private Calendar creationDate;
	private Calendar editionDate;
	private String name;
	private String email;
	private String address;
	private String city;
	private String state;
	private Double latitude;
	private Double longitude;
	private String period;
	private String telephoneNumber;
	private String status;
	private Integer associated;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getAssociated() {
		return associated;
	}
	public void setAssociated(Integer associated) {
		this.associated = associated;
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
		InstitutionRequest other = (InstitutionRequest) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
