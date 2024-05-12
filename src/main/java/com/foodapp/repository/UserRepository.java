package com.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodapp.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	
	public User findByEmail(String userEmail);
}
