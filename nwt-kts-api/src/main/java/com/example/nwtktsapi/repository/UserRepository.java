package com.example.nwtktsapi.repository;

import com.example.nwtktsapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

	User findByEmail(String email);
}
