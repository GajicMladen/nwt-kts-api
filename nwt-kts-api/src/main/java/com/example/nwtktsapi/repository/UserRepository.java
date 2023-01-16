package com.example.nwtktsapi.repository;

import com.example.nwtktsapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

	User findByEmail(String email);

	List<User> findByRoles_Id(Long id);



}
