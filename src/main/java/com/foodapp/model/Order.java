package com.foodapp.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	private User customer;
	@ManyToOne
	private Restaurent restaurent;
	
	private Long totalAmount;
	private String orderStatus;
	
	private Date createdOn;
	@ManyToOne 
	private Address deliveryAddress;
	
	@OneToMany
	private List<OrderItems> items;
	
	private int totalItem;
	
	private int totalPrice;
	
	
	// private Payment paymentMethod;
}
