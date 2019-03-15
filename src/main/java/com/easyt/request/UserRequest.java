package com.easyt.request;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import com.easyt.constant.DocumentCategoryEnum;
import com.easyt.constant.PeriodEnum;

public class UserRequest implements Serializable {
	private static final long serialVersionUID = -6720688576325816002L;
	
	private Long id;
	private String name;
	private String email;
	private String cpf;
	private String telephoneNumber;
	private String mediaUrl;
	private Calendar dateOfBirth;
	private String pictureDocument;
	
	// TODO: FAZER ISSO QUANDO HOUVER VINCULAÇÃO DE MOTORISTA COM ALUNO
	private Double radius;
	private String companyName;
	private PeriodEnum period;
	private String address;
	private String city;
	private String state;
	private DocumentCategoryEnum documentCategory;
	private String documentRegisterNumber;
	
	private String changePassword;
	private String token;
	private String password;
	private String newPassword;
	private String confirmPassword;
	private List<PermissionRequest> permissions;
	
	// GETTERS AND SETTERS
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getChangePassword() {
		return changePassword;
	}
	public void setChangePassword(String changePassword) {
		this.changePassword = changePassword;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Double getRadius() {
		return radius;
	}
	public void setRadius(Double radius) {
		this.radius = radius;
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
	public List<PermissionRequest> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<PermissionRequest> permissions) {
		this.permissions = permissions;
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
		UserRequest other = (UserRequest) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
