package com.example.nwtktsapi.dto;

public class ResetPasswordDTO {
	private String newPassword;
	private String confirmNewPassword;
	private String token;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
	
	public boolean validate() {
		return newPassword.equals(confirmNewPassword) && newPassword.length() >= 8 && newPassword.length() <= 30;
	}
	
}
