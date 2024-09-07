package com.semicolon.africa.data.repository;

import com.semicolon.africa.data.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, String>{
    Optional<Admin> findByUserName(String userName);
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByPhoneNumber(String phoneNumber);
}
