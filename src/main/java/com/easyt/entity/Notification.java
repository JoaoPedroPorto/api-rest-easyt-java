package com.easyt.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.easyt.constant.StatusEnum;
import com.easyt.constant.TargetAudienceEnum;


@Entity
@Table(name="NOTIFICATION")
public class Notification implements Serializable {
	private static final long serialVersionUID = 4693205752497884037L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NOTIFICATION_ID")
	private Long id;
	
	@Column(name = "NOTIFICATION_ID_ASSOCIATED_TYPE")
	private Long idAssociatedType;
	
	@Column(name = "NOTIFICATION_CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar creationDate;
	
	@Column(name = "NOTIFICATION_EDITION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar editionDate;
	
	@Column(name = "NOTIFICATION_TITLE")
	private String title;
	
	@Column(name = "NOTIFICATION_TEXT", length=300)
	private String text;
	
	@Column(name = "NOTIFICATION_TARGET_AUDIENCE")
	@Enumerated(EnumType.STRING)
	private TargetAudienceEnum targetAudience;
	
	@Column(name = "NOTIFICATION_STATUS")
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	
	// RELATIONSHIPS
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="id.notification")
	private List<NotificationUser> users;

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
	public TargetAudienceEnum getTargetAudience() {
		return targetAudience;
	}
	public void setTargetAudience(TargetAudienceEnum targetAudience) {
		this.targetAudience = targetAudience;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public List<NotificationUser> getUsers() {
		return users;
	}
	public void setUsers(List<NotificationUser> users) {
		this.users = users;
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
		Notification other = (Notification) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
