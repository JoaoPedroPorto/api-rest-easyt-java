package com.easyt.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.easyt.constant.DocumentCategoryEnum;
import com.easyt.constant.PeriodEnum;
import com.easyt.constant.ProfileEnum;
import com.easyt.constant.StatusEnum;


@Entity
@Table(name="USER")
public class User implements Serializable {
	private static final long serialVersionUID = -8969897278944475153L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long id;

	@Column(name = "USER_CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar creationDate;
	
	@Column(name = "USER_EDITION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar editionDate;
	
	@Column(name = "USER_DATE_OF_BIRTH")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateOfBirth;
	
	@Column(name = "USER_MEDIA_URL")
	private String mediaUrl;
	
	@Column(name = "USER_PICTURE_DOCUMENT")
	private String pictureDocument;
	
	@Column(name = "USER_NAME")
	private String name;
	
	@Column(name = "USER_EMAIL")
	private String email;
	
	@Column(name = "USER_GOOGLE_ID")
	private String googleId;
	
	@Column(name = "USER_FACEBOOK_ID")
	private String facebookId;
	
	@Column(name = "USER_PASSWORD")
	private String password;
	
	@Column(name = "USER_PROFILE")
	@Enumerated(EnumType.STRING)
	private ProfileEnum profile;
	
	@Column(name = "USER_CPF")
	private String cpf;
	
	@Column(name = "USER_PERIOD")
	@Enumerated(EnumType.STRING)
	private PeriodEnum period;
	
	@Column(name = "USER_ADDRESS")
	private String address;
	
	@Column(name = "USER_CITY")
	private String city;
	
	@Column(name = "USER_STATE")
	private String state;
	
	@Column(name = "USER_COMPANY_NAME")
	private String companyName;
	
	@Column(name = "USER_DOCUMENT_CATEGORY")
	@Enumerated(EnumType.STRING)
	private DocumentCategoryEnum documentCategory;
	
	@Column(name = "USER_DOCUMENT_REGISTER_NUMBER")
	private String documentRegisterNumber;
	
	@Column(name = "USER_RADIUS")
	private Double radius;
	
	@Column(name = "USER_TELEPHONE_NUMBER")
	private String telephoneNumber;
	
	@Column(name = "USER_STATUS")
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	
	// RELATIONSHIPS
	
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE},fetch=FetchType.LAZY)
    @JoinTable(name = "USER_INSTITUTION", 
        joinColumns=@JoinColumn(name="USER_INSTITUTION_FK_USER"),
        inverseJoinColumns=@JoinColumn(name="USER_INSTITUTION_FK_INSTITUTION"))
    private List<Institution> institutions;
	
	@OneToOne(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST})
	@JoinColumn(name = "USER_FK_DRIVER")
	private User driver;
	
	/*
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
	private List<Vehicle> vehicles;
	
	*/
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
	private List<Token> tokens;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
	private List<Device> devices;

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
	public Calendar getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Calendar dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getMediaUrl() {
		return mediaUrl;
	}
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
	public String getPictureDocument() {
		return pictureDocument;
	}
	public void setPictureDocument(String pictureDocument) {
		this.pictureDocument = pictureDocument;
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
	public String getGoogleId() {
		return googleId;
	}
	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}
	public String getFacebookId() {
		return facebookId;
	}
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ProfileEnum getProfile() {
		return profile;
	}
	public void setProfile(ProfileEnum profile) {
		this.profile = profile;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public PeriodEnum getPeriod() {
		return period;
	}
	public void setPeriod(PeriodEnum period) {
		this.period = period;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public DocumentCategoryEnum getDocumentCategory() {
		return documentCategory;
	}
	public void setDocumentCategory(DocumentCategoryEnum documentCategory) {
		this.documentCategory = documentCategory;
	}
	public String getDocumentRegisterNumber() {
		return documentRegisterNumber;
	}
	public void setDocumentRegisterNumber(String documentRegisterNumber) {
		this.documentRegisterNumber = documentRegisterNumber;
	}
	public Double getRadius() {
		return radius;
	}
	public void setRadius(Double radius) {
		this.radius = radius;
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
	public List<Token> getTokens() {
		return tokens;
	}
	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}
	public List<Device> getDevices() {
		return devices;
	}
	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
	public List<Institution> getInstitutions() {
		return institutions;
	}
	public void setInstitutions(List<Institution> institutions) {
		this.institutions = institutions;
	}
	public User getDriver() {
		return driver;
	}
	public void setDriver(User driver) {
		this.driver = driver;
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
