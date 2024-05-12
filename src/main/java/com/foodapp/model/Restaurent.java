package com.foodapp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Restaurent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private User owner;
	private String name;
	
	private String description;
	private String cuisineType;
	
	@OneToOne
	private Address address;
	
	@Embedded
	private ContactInformation contactInformation;
	
	
	private String openningHours;
	@OneToMany(mappedBy = "restaurent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Order> orders=new  ArrayList<>();
	@ElementCollection
	@Column(length = 1000)
	private List<String> images;
	
	private LocalDateTime registrationDate;
	
	private boolean open;
	@OneToMany(mappedBy = "restaurent",cascade = CascadeType.ALL)
	private List<Food> foods=new ArrayList<>();
	
	
	
	
	
	
	
	
}
