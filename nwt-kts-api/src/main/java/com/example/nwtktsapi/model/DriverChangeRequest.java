package com.example.nwtktsapi.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.example.nwtktsapi.dto.ChangeUserDataDTO;

@Entity
public class DriverChangeRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "lastName")
	private String lastName;
	
	@Column(name = "town")
	private String town;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "profile_photo")
	private String profilePhoto;
	
	@Column(name = "resolved", columnDefinition = "boolean default false")
	private boolean resolved;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User driver;
	
	public DriverChangeRequest() {}

	public DriverChangeRequest(Long id, String name, String lastName, String town, String phone, String profilePhoto,
			User driver) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.town = town;
		this.phone = phone;
		this.profilePhoto = profilePhoto;
		this.driver = driver;
		this.resolved = false;
	}

	public DriverChangeRequest(ChangeUserDataDTO dto, User driver) {
		this.name = dto.getName();
		this.lastName = dto.getLastName();
		this.town = dto.getTown();
		this.phone = dto.getPhone();
		this.profilePhoto = dto.getPhoto();
		this.driver = driver;
		this.resolved = false;
	}
	
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean approved) {
		this.resolved = approved;
	}

	public User getDriver() {
		return driver;
	}

	public void setDriver(User driver) {
		this.driver = driver;
	}
	
	
}
