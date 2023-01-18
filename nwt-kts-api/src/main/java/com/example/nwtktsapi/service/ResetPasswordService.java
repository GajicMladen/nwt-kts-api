package com.example.nwtktsapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nwtktsapi.model.ResetPassword;
import com.example.nwtktsapi.repository.ResetPasswordRepository;

@Service
public class ResetPasswordService {

	@Autowired
	ResetPasswordRepository resetPasswordRepository;
	
	public void saveRequest(ResetPassword reset) {
		this.resetPasswordRepository.save(reset);
	}
	
	public ResetPassword getByToken(String token) {
		return this.resetPasswordRepository.getByToken(token);
	}
}
