package io.javabrains.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.javabrains.model.Movie;
import io.javabrains.model.User;
import io.javabrains.service.DocumentDBUserService;
import io.javabrains.service.HttpsService;

@RestController
@RequestMapping("/movies")
public class MovieResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieResource.class);

	@Autowired
	private DocumentDBUserService userService;

	@Autowired
	private HttpsService httpsService;

	@GetMapping("/{movieId}")
	public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
		return new Movie(movieId, "Transformer");
	}

	@GetMapping("/users/list")
	public List<User> getUsers() {
		LOGGER.info("GET USER LIST");
		return userService.getUserList();
	}

	@PostMapping("/users")
	public List<User> addUser(@RequestBody User user) {
		LOGGER.info("ADD USER " + user.getUserId());
		userService.addUser(user);
		return userService.getUserList();
	}

	@DeleteMapping("/users/{userId}")
	public Integer deleteUser(@PathVariable("userId") int userId) {
		LOGGER.info("DELETE USER " + userId);
		return userService.deleteUser(userId);
	}

	@PutMapping("/users/{userId}")
	public User updateUser(@RequestBody User user, @PathVariable("userId") int userId) {
		LOGGER.info("UPDATE USER " + userId);
		return userService.updateUser(user, userId);
	}

	@GetMapping("/users/secure")
	public String getsecure() {
		LOGGER.info("GET USER LIST");
		return httpsService.getHttsConnection();
	}
	
	@GetMapping("/test")
	public Movie getTest() {
		System.out.println("MovieResource IN TEST CALL ");
		return new Movie("SUCCESS", "200");
	}

}
