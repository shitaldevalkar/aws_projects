package io.javabrains.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.javabrains.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
	public void deleteByUserId(int userId);
	public Optional<User> findByUserId(int userId);
}
