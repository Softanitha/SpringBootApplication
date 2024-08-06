package com.microservice.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.model.Movie;
import com.microservice.repo.MovieRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerIntTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	MovieRepository movierepository;

	@BeforeEach
	void clenUp() {
		movierepository.deleteAllInBatch();
	}

	@Test
	public void createMovie() throws Exception {// created deatils
		Movie movie = new Movie();
		movie.setName("raja");
		movie.setDirector("ss rajamouli");
		movie.setActors(List.of("venkatesh", "soudrya"));

		var response = mockMvc.perform(post("/movies").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(movie)));

		response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(notNullValue())))
				.andExpect(jsonPath("$.name", is(movie.getName())))
				.andExpect(jsonPath("$.director", is(movie.getDirector())))
				.andExpect(jsonPath("$.actors", is(movie.getActors())));

	}

	@Test
	public void gettingMovie() throws Exception {// getting detailsby id
		Movie movie = new Movie();
		movie.setName("raja");
		movie.setDirector("ss rajamouli");
		movie.setActors(List.of("venkatesh", "soudrya"));
		Movie saveMovie = movierepository.save(movie);

		var response = mockMvc.perform(get("/movies/" + saveMovie.getId()));

		response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(saveMovie.getId().intValue())))
				.andExpect(jsonPath("$.name", is(movie.getName())))
				.andExpect(jsonPath("$.director", is(movie.getDirector())))
				.andExpect(jsonPath("$.actors", is(movie.getActors())));

	}

	@Test
	public void updateMovie() throws Exception { // updating details

		Movie movie = new Movie();
		movie.setName("raja");
		movie.setDirector("ss rajamouli");
		movie.setActors(List.of("venkatesh", "soudrya"));
		Movie saveMovie = movierepository.save(movie);
		Long id = saveMovie.getId();
		movie.setActors(List.of("venkatesh", "soudrya", "balu"));
		var response = mockMvc.perform(put("/movies/" + id).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(movie)));
		response.andDo(print()).andExpect(status().isOk());

		var fetchResponse = mockMvc.perform(get("/movies/" + id));
		fetchResponse.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name", is(movie.getName())))
				.andExpect(jsonPath("$.director", is(movie.getDirector())))
				.andExpect(jsonPath("$.actors", is(movie.getActors())));

	}
	
	@Test
	public void deleteMovie() throws Exception{
		Movie movie = new Movie();
		movie.setName("raja");
		movie.setDirector("ss rajamouli");
		movie.setActors(List.of("venkatesh", "soudrya"));
		Movie saveMovie = movierepository.save(movie);
		Long id = saveMovie.getId();
		mockMvc.perform(delete("/movies/" + id))
		.andDo(print()).andExpect(status().isOk());
	
		movierepository.findById(id).isPresent();
	}

	
}
