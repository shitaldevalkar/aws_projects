package io.javabrains.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.javabrains.model.User;
import io.javabrains.repository.UserRepository;

@Service
public class UserRepositoryService {

	@Autowired
	private UserRepository userRepository;

	public List<User> getUserList() {
		List<User> userList = userRepository.findAll();
		Collections.sort(userList);
		return userList;
	}

	public User addUser(User user) {
		int topIndex = 0;
		List<User> userList = getUserList();
		for (User user1 : userList) {
			if (topIndex < user1.getUserId()) {
				topIndex = user1.getUserId();
			}
		}
		user.setUserId(topIndex + 1);
		userRepository.save(user);
		return user;
	}

	public int deleteUser(int userId) {
		userRepository.deleteByUserId(userId);
		return userId;
	}

	public User updateUser(User user, int userId) {
		Optional<User> userResult = userRepository.findByUserId(userId);
		if (userResult.isPresent()) {
			User oldUser = userResult.get();
			user.setId(oldUser.getId());
			userRepository.save(user);
		}
		return user;
	}
}
