package com.microservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
