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

import com.easyt.constant.PeriodEnum;
import com.easyt.constant.StatusEnum;


@Entity
@Table(name="INSTITUTION")
public class Institution implements Serializable {
	private static final long serialVersionUID = 7535479985392759497L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INSTITUTION_ID")
	private Long id;
	
	@Column(name = "INSTITUTION_CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar creationDate;
	
	@Column(name = "INSTITUTION_EDITION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar editionDate;
	
	@Column(name = "INSTITUTION_NAME")
	private String name;
	
	@Column(name = "INSTITUTION_EMAIL")
	private String email;
	
	@Column(name = "INSTITUTION_ADDRESS")
	private String address;
	
	@Column(name = "INSTITUTION_CITY")
	private String city;
	
	@Column(name = "INSTITUTION_STATE")
	private String state;
	
	@Column(name = "INSTITUTION_LATITUDE")
	private Double latitude;
	
	@Column(name = "INSTITUTION_LONGITUDE")
	private Double longitude;
	
	@Column(name = "INSTITUTION_PERIOD")
	@Enumerated(EnumType.STRING)
	private PeriodEnum period;
	
	@Column(name = "INSTITUTION_TELEPHONE_NUMBER")
	private String telephoneNumber;
	
	@Column(name = "INSTITUTION_STATUS")
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
	public PeriodEnum getPeriod() {
		return period;
	}
	public void setPeriod(PeriodEnum period) {
		this.period = period;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
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
		Institution other = (Institution) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
