package com.semicolon.africa.Data.Repository;

import com.semicolon.africa.Data.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



import java.util.Optional;

    @Repository
    public interface UserRepository extends MongoRepository<User, String> {
        Optional<User> findByEmail(String email);
        Optional<User> findByUserName(String username);
    }


