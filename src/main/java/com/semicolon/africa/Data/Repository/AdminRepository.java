package com.semicolon.africa.Data.Repository;

import com.semicolon.africa.Data.Model.Admin;
import com.semicolon.africa.Data.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {
    List<User> findByFirstName(String firstName);
    Optional<Admin> findByEmail(String email);
}
