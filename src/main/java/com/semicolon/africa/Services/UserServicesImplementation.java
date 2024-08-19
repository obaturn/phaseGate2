package com.semicolon.africa.Services;

import com.semicolon.africa.Data.Model.User;
import com.semicolon.africa.Data.Repository.UserRepository;
import com.semicolon.africa.Dto.*;
import com.semicolon.africa.Exceptions.UserLoginExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImplementation implements UserServices {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) {
        User user = new User();
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(userRegisterRequest.getPassword());
        user.setPhoneNumber(userRegisterRequest.getPhoneNumber());
        user.setUserName(userRegisterRequest.getUserName());
        user.setAddress(userRegisterRequest.getAddress());
        userRepository.save(user);
        UserRegisterResponse registerResponse = new UserRegisterResponse();
        registerResponse.setMessage("Successfully registered");
        return registerResponse;
    }

    @Override
    public UserLoginResponse UserLogin(UserLoginRequest login) {
        Optional<User> userOptional = userRepository.findByEmail(login.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getPassword().equals(login.getPassword())) {
                if (user.getUserName().equals(login.getUserName())) {
                    user.setLogin(true);
                    UserLoginResponse loginResponse = new UserLoginResponse();
                    loginResponse.setMessage("login successful");
                    return loginResponse;
                } else {
                    throw new UserLoginExceptions("Incorrect username. Check your username carefully.");
                }
            } else {
                throw new UserLoginExceptions("Invalid password.");
            }
        } else {
            throw new UserLoginExceptions("User not found with email: " + login.getEmail());
        }
    }

    @Override
    public UserLogOutResponse userLogOut(UserLogOutRequest userLogOutRequest) {
        Optional<User> user = userRepository.findByUserName(userLogOutRequest.getUserName());
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setLogin(false);
            userRepository.save(user1);
            UserLogOutResponse userLogOutResponse = new UserLogOutResponse();
            userLogOutResponse.setMessage("user logout successful");
            return userLogOutResponse;
        } else {
            throw new UserLoginExceptions("incorrect username");
        }

    }

    @Override
    public UserCreatePasswordResponse createPassword(UserCreatePasswordRequest userCreatePasswordRequest) {
        String password = userCreatePasswordRequest.getPassword();
        if (password.length() != 7) {
            throw new UserLoginExceptions("password must be up to 7 characters");
        } else {
            User user = new User();
            user.setFirstName(userCreatePasswordRequest.getFirstName());
            user.setLastName(userCreatePasswordRequest.getLastName());
            user.setPassword(password);
            userRepository.save(user);
            UserCreatePasswordResponse createPasswordResponse = new UserCreatePasswordResponse();
            createPasswordResponse.setMessage("password created successfully");
            return createPasswordResponse;
        }
    }
}



