package com.example.nwtktsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nwtktsapi.model.ResetPassword;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {
	public ResetPassword getByToken(String token);
}
