package com.semicolon.africa.data.repository;

import com.semicolon.africa.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
