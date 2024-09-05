package com.semicolon.africa.data.repository;

import com.semicolon.africa.data.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, String> {
}
