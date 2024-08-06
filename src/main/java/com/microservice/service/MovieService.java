package com.microservice.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.exception.InvalidDataException;
import com.microservice.exception.NotFoundException;
import com.microservice.model.Movie;
import com.microservice.repo.MovieRepository;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repo;
	
	public Movie create(Movie movie) {
		if(movie==null) {
			throw new InvalidDataException("Invalid movies:null");
		}
		
		return repo.save(movie);
	}
	
	
	public  List<Movie> displayAll() {
		return repo.findAll();
	}
	
	
	public Movie read(Long id) {
		return repo.findById(id)
				.orElseThrow(()-> new NotFoundException("Movie not found:"+id));
	}
	
	public void update(Long id,Movie updatemovie) {
		if(updatemovie==null || id==null) {
			throw new InvalidDataException("Invalid Movies:null");
		}
		 if(repo.existsById(id)) {
			 Movie movie=repo.getReferenceById(id);
			 movie.setName(updatemovie.getName());
			 movie.setDirector(updatemovie.getDirector());
			 movie.setActors(updatemovie.getActors());
			  repo.save(movie);
			
		}
		 else {
			 throw new NotFoundException("movie not found:"+id);
		 }
	}
	
	public void delete(Long id) {
		if(repo.existsById(id)) {
			repo.deleteById(id);		
			
		}else {
			throw new NotFoundException("movie not found:"+id);
		}
	}
	
	
}
