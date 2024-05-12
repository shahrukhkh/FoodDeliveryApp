package com.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodapp.model.Cart;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	
	
}
