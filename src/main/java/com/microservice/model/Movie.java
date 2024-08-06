package com.microservice.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Movie {
	
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String director;
	private List<String>actors=new  ArrayList<>();

}
