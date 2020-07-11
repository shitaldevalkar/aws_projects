package io.javabrains.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import io.javabrains.model.User;

@Service
public class UserService {

	private List<User> userList;

	public UserService() {
		this.userList = new ArrayList<>();
		userList.add(new User(1, "Shital", "Devalkar", "devalkars@gmail.com"));
	}

	public List<User> getUserList() {
		Collections.sort(userList);
		return userList;
	}

	public User addUser(User user) {
		int topIndex = 0;
		for (User user1 : userList) {
			if (topIndex < user1.getUserId()) {
				topIndex = user1.getUserId();
			}
		}
		user.setUserId(topIndex+1);
		userList.add(user);
		return user;
	}

	public int deleteUser(int id) {
		for (int idx = userList.size() - 1; idx >= 0; idx--) {
			if (userList.get(idx).getUserId() == id) {
				userList.remove(idx);
				break;
			}
		}
		return id;
	}

	public User updateUser(User user, int id) {
		for (int idx = userList.size() - 1; idx >= 0; idx--) {
			if (userList.get(idx).getUserId() == id) {
				userList.set(idx, user);
				return userList.get(idx);
			}
		}
		return null;
	}
}

