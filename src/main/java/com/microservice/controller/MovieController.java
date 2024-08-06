package com.microservice.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.model.Movie;
import com.microservice.service.MovieService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/movies")
@Slf4j
public class MovieController {

	@Autowired
	private MovieService movieservice;

	@GetMapping("/{id}")
	public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
		Movie movie = movieservice.read(id);
		log.info("returned movie with id:{}",id);
		return ResponseEntity.ok(movie);
	}

	@PostMapping
	public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {

		Movie createMovie = movieservice.create(movie);
		log.info("created movie with id:{}",createMovie.getId());
		return ResponseEntity.ok(createMovie);

	}
	
	@GetMapping("/all")
	public List<Movie> display(){
		return movieservice.displayAll();
		
		
	}

	@PutMapping("/{id}")
	public void updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
		
		movieservice.update(id, movie);
		log.info("updated movie with id:{}",id);


	}

	@DeleteMapping("/{id}")
	public void deleteMovie(@PathVariable Long id) {
		
		movieservice.delete(id);
		log.info("deleted movie with id:{}",id);
	}

}
