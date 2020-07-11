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
import io.javabrains.service.UserRepositoryService;

@RestController
@RequestMapping("/movies/repo")
public class UserRepositoryResource {

	@Autowired
	private UserRepositoryService userRepoService;

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieResource.class);

	@GetMapping("/users/list")
	public List<User> getUsers() {
		LOGGER.info("REPO GET USERS:");
		return userRepoService.getUserList();
	}

	@PostMapping("/users")
	public List<User> addUser(@RequestBody User user) {
		LOGGER.info("REPO ADD USER " + user.getUserId());
		userRepoService.addUser(user);
		return userRepoService.getUserList();
	}

	@PutMapping("/users/{userId}")
	public User updateUser(@RequestBody User user, @PathVariable("userId") int userId) {
		LOGGER.info("REPO UPDATE USER " + userId);
		return userRepoService.updateUser(user, userId);
	}

	@DeleteMapping("/users/{userId}")
	public Integer deleteUser(@PathVariable("userId") int userId) {
		LOGGER.info("REPO DELETE USER " + userId);
		return userRepoService.deleteUser(userId);
	}

	@GetMapping("/test")
	public Movie getTest() {
		System.out.println("UserRepository IN TEST CALL ");
		return new Movie("SUCCESS", "200");
	}

}
