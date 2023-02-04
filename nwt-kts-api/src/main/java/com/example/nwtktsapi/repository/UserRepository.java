package com.example.nwtktsapi.repository;

import com.example.nwtktsapi.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

	User findByEmail(String email);

	List<User> findByRoles_Id(Long id);

	@Query("select u from User u inner join u.roles r where u.active = true and u.blocked = false and r.id = ?1")
	List<User> findUsersByTypePagination(Long id, Pageable pageable);
	@Query("select u from User u where u.id = ?1 and u.inRide = true")
	User clientInRide(Long id);


	@Query("select count(u) from User u inner join u.roles r where u.active = true and u.blocked = false and r.id = ?1")
	int getUserByTypeCount(Long id);

	@Query("select u from User u inner join u.roles r where u.active = true and u.blocked = false and (r.id = 1 or r.id = 3)")
	List<User> getClientsAndDrivers();
}
