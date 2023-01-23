package com.example.nwtktsapi.dto;

import org.springframework.util.StringUtils;

public class ChangeUserDataDTO {
	String photo;
	String name;
	String lastName;
	String phone;
	String town;
	
	ChangeUserDataDTO(){}
	
	public String getPhoto() {
		return photo;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}
	
	public boolean validate() {
		return phone.matches("^[0-9]{9,12}$");
	}
	
	public void casify() {
		this.name = toTitleCase(this.name);
		this.lastName = toTitleCase(this.lastName);
		this.town = toTitleCase(this.town);
	}
	
	public String toTitleCase(String str) {
		String[] tokens = str.split(" ");
		String res = "";
		for(String token: tokens)
			res += StringUtils.capitalize(token.toLowerCase()) + " ";
		return res.trim();
	}
	
	
}
