package com.easyt.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.easyt.constant.VerificationTokenType;
import com.easyt.util.ApplicationUtil;

@Entity
@Table(name="TOKEN")
public class Token implements Serializable {
	private static final long serialVersionUID = 7616104683467214024L;
	private static final int TIME_EXPIRATION_PATTERN_IN_MINUTES = 30; // 30 MINUTES
	public static final int TIME_EXPIRATION_SESSION_IN_TWO_HOUR = 60 * 2; // 120 MINUTES
	public static final int TIME_EXPIRATION_SESSION_IN_THREE_HOUR = 60 * 3; // 180 MINUTES

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column(name="TOKEN_ID")
	private Long id;
	
	@Column(name="TOKEN_VALUE", length = 100 )
    private String value;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TOKEN_DATE_OF_EXPIRATION" )
    private Date dateOfExpiration;
	
	@Column(name="TOKEN_TYPE" )
	@Enumerated(EnumType.STRING)
    private VerificationTokenType type;
	
	@ManyToOne
	@JoinColumn(name="TOKEN_FK_USER",updatable = false)
    private User user;
	
	// GETTERS AND SETTERS
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getDateOfExpiration() {
		return dateOfExpiration;
	}
	public void setDateOfExpiration(Date dateOfExpiration) {
		this.dateOfExpiration = dateOfExpiration;
	}
	public VerificationTokenType getType() {
		return type;
	}
	public void setType(VerificationTokenType type) {
		this.type = type;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	// OTHERS
	
	public Token() {
		this.value = UUID.randomUUID().toString();
	}

	public Token(User user, VerificationTokenType type) {
		this();
		this.user = user;
		this.type = type;
		if (type.equals(VerificationTokenType.SESSION)) {
			this.dateOfExpiration = ApplicationUtil.calculateExpiryDate(TIME_EXPIRATION_SESSION_IN_TWO_HOUR);
		} else if (type.equals(VerificationTokenType.REFRESH_SESSION)) {
			this.dateOfExpiration = ApplicationUtil.calculateExpiryDate(TIME_EXPIRATION_SESSION_IN_THREE_HOUR);			
		} else if (type.equals(VerificationTokenType.REDEFINE_PASSWORD)) {
			this.dateOfExpiration = ApplicationUtil.calculateExpiryDate(TIME_EXPIRATION_PATTERN_IN_MINUTES);
		}
	}

	public Boolean hasExpired() {
		LocalDateTime tokenDate = LocalDateTime.ofInstant(getDateOfExpiration().toInstant(), ZoneId.systemDefault());
		return tokenDate.isBefore(LocalDateTime.now());
	}
	
}