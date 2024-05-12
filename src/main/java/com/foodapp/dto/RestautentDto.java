package com.foodapp.dto;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Embeddable
public class RestautentDto {

	
	private String title;
	@Column(length = 1000)
	private List<String> images;
	private String description;
	private Long Id;
}
