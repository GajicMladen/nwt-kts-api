package com.example.nwtktsapi.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.nwtktsapi.enums.ResetPasswordStatus;

@Entity
public class ResetPassword {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name = "token", nullable = false)
	private String token;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "expiration_date", nullable = false)
	private LocalDateTime expirationDate;
	
	@Column(name = "status", nullable = false)
	private ResetPasswordStatus status;
	
	
	
	public ResetPassword() {
	}

	public ResetPassword(String token, String email, LocalDateTime expirationDate) {
		super();
		this.token = token;
		this.email = email;
		this.expirationDate = expirationDate;
		this.status = ResetPasswordStatus.UNRESOLVED;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}

	public ResetPasswordStatus getStatus() {
		return status;
	}

	public void setStatus(ResetPasswordStatus status) {
		this.status = status;
	}
	
}
