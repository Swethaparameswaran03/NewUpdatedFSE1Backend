package com.tweetapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	public Optional<List<User>> findByUsername(String userName);

	public List<User> findByUsernameContains(String username);

	public void deleteByUsername(String username);

}
