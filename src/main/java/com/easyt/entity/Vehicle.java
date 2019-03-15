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

import com.easyt.constant.FuelEnum;
import com.easyt.constant.StatusEnum;


@Entity
@Table(name="VEHICLE")
public class Vehicle implements Serializable {
	private static final long serialVersionUID = -8576771599203332523L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VEHICLE_ID")
	private Long id;
	
	@Column(name = "VEHICLE_CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar creationDate;
	
	@Column(name = "VEHICLE_EDITION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar editionDate;
	
	@Column(name = "VEHICLE_RENAVAM_CODE")
	private String renavamCode;
	
	@Column(name = "VEHICLE_BRAND_MODEL")
	private String brandModel;
	
	@Column(name = "VEHICLE_LICENSE_PLATE")
	private String licensePlate;
	
	@Column(name = "VEHICLE_FUEL")
	@Enumerated(EnumType.STRING)
	private FuelEnum fuel;
	
	@Column(name = "VEHICLE_FABRICATION_YEAR")
	private Integer fabricationYear;
	
	@Column(name = "VEHICLE_MODEL_YEAR")
	private Integer modelYear;
	
	@Column(name = "VEHICLE_QUANTITY_OF_PLACES")
	private Integer quantityOfPlaces;
	
	@Column(name = "VEHICLE_KILOMETER")
	private Double kilometer;
	
	@Column(name = "VEHICLE_STATUS")
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	
	@Column(name = "VEHICLE_PICTURE_DOCUMENT")
	private String pictureDocument;
	
	@Column(name = "VEHICLE_COLOR")
	private String color;
	
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
	public String getRenavamCode() {
		return renavamCode;
	}
	public void setRenavamCode(String renavamCode) {
		this.renavamCode = renavamCode;
	}
	public String getBrandModel() {
		return brandModel;
	}
	public void setBrandModel(String brandModel) {
		this.brandModel = brandModel;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public FuelEnum getFuel() {
		return fuel;
	}
	public void setFuel(FuelEnum fuel) {
		this.fuel = fuel;
	}
	public Integer getFabricationYear() {
		return fabricationYear;
	}
	public void setFabricationYear(Integer fabricationYear) {
		this.fabricationYear = fabricationYear;
	}
	public Integer getModelYear() {
		return modelYear;
	}
	public void setModelYear(Integer modelYear) {
		this.modelYear = modelYear;
	}
	public Integer getQuantityOfPlaces() {
		return quantityOfPlaces;
	}
	public void setQuantityOfPlaces(Integer quantityOfPlaces) {
		this.quantityOfPlaces = quantityOfPlaces;
	}
	public Double getKilometer() {
		return kilometer;
	}
	public void setKilometer(Double kilometer) {
		this.kilometer = kilometer;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public String getPictureDocument() {
		return pictureDocument;
	}
	public void setPictureDocument(String pictureDocument) {
		this.pictureDocument = pictureDocument;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
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
		Vehicle other = (Vehicle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
