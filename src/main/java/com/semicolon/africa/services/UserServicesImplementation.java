package com.semicolon.africa.services;


import com.semicolon.africa.data.model.User;
import com.semicolon.africa.data.repository.UserRepository;
import com.semicolon.africa.dto.UserRegisterRequest;
import com.semicolon.africa.dto.UserView;
import com.semicolon.africa.dto.ViewRequest;
import com.semicolon.africa.dto.userRegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServicesImplementation implements UserServices {
    @Autowired
    private  UserRepository userRepository;
    @Override
    public userRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) {
        User user = new User();
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(userRegisterRequest.getPassword());
        user.setUserName(userRegisterRequest.getUserName());
        user.setPhoneNumber(userRegisterRequest.getPhoneNumber());
        userRepository.save(user);
        userRegisterResponse registerResponse = new userRegisterResponse();
        registerResponse.setMessage("you have been successfully registered");
        registerResponse.setStatusMessage("200 created successfully");
        registerResponse.setFirstName(userRegisterRequest.getFirstName());
        registerResponse.setLastName(userRegisterRequest.getLastName());
        registerResponse.setUserName(userRegisterRequest.getUserName());
        return registerResponse;
    }


    @Override
    public UserView viewerUser(ViewRequest view) {
        return null;
    }
}
