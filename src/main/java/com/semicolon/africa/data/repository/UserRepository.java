package com.semicolon.africa.data.repository;

import com.semicolon.africa.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String username);
    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
    Optional<User> findByPhoneNumber(String phoneNumber);
    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);
}
