package io.javabrains.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.javabrains.model.Movie;
import io.javabrains.service.AmazonEmailSenderService;

@RestController
@RequestMapping("/catlog")
public class MovieCatlogResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieCatlogResource.class);
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AmazonEmailSenderService emailService;

	@GetMapping("/users/sendEmail/{emailId}")
	public String sendEmail(@PathVariable("emailId") String emailId) {
		System.out.println("sendEmail  " + emailId);
		emailService.sendEmail(emailId);
		return "{\"success\" : \"true\"}";
	}

	@GetMapping("/loadbalanced1/{movieId}")
	public Movie getMovieInfoLoadBalanced1(@PathVariable("movieId") String movieId) {
		LOGGER.info("In the test logger loadbalancer1");
		Movie movie = restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movies/" + movieId, Movie.class);
		return movie;
	}

	@GetMapping("/loadbalanced2/{movieId}")
	public Movie getMovieInfoLoadBalanced2(@PathVariable("movieId") String movieId) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + movieId, Movie.class);
		return movie;
	}

	@GetMapping("/regular/{movieId}")
	public Movie getMovieInfoRegular(@PathVariable("movieId") String movieId) {
		System.out.println("BEFORE CALL " + movieId);
		try {
			Movie movie = new RestTemplate().getForObject("http://10.0.2.14:8082/movies/" + movieId, Movie.class);
			return movie;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new Movie("Error", "200");
	}

	@GetMapping("/test")
	public Movie getTest() {
		System.out.println("MovieCatlogResource IN TEST CALL ");
		return new Movie("SUCCESS", "200");
	}
}
