package com.semicolon.africa.services;

import com.semicolon.africa.data.model.User;
import com.semicolon.africa.data.repository.UserRepository;
import com.semicolon.africa.dto.*;
import com.semicolon.africa.exceptions.UserRegisterExceptions;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServicesImplementation implements UserServices {
    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) {
        try{
            validateUserRegistration(userRegisterRequest);
        }catch (UserRegisterExceptions e){
            throw new UserRegisterExceptions(e.getMessage());
        }
        User user = new User();
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        user.setPhoneNumber(userRegisterRequest.getPhoneNumber());
        user.setGender(userRegisterRequest.getGender());
        user.setUserName(userRegisterRequest.getUserName());
        user.setAddress(userRegisterRequest.getAddress());
        user.setConfirmPassword(passwordEncoder.encode(userRegisterRequest.getConfirmPassword()));
        userRepository.save(user);
        UserRegisterResponse response = new UserRegisterResponse();
        response.setMessage("you have been registered successfully");
        response.setFirstName(userRegisterRequest.getFirstName());
        response.setLastName(userRegisterRequest.getLastName());
        response.setStatusMessage("200 created successfully");
        return response;
    }


    private void validateUserRegistration(UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest.getFirstName() == null || userRegisterRequest.getFirstName().trim().isEmpty()) {
            throw new UserRegisterExceptions("first name is required pls input firstName");

        }
        if (userRegisterRequest.getLastName() == null || userRegisterRequest.getLastName().trim().isEmpty()) {
            throw new UserRegisterExceptions("last name is required pls input lastName");
        }
        if (userRegisterRequest.getEmail() == null || userRegisterRequest.getEmail().trim().isEmpty()) {
            throw new UserRegisterExceptions("email is required pls input email");
        }
        if (userRegisterRequest.getPhoneNumber() == null || userRegisterRequest.getPhoneNumber().trim().isEmpty()) {
            throw new UserRegisterExceptions("phone number is required pls input phone number");
        }
        if (userRegisterRequest.getPassword() == null || userRegisterRequest.getPassword().trim().isEmpty()) {
            throw new UserRegisterExceptions("password is required pls input password");
        }
        if (userRegisterRequest.getConfirmPassword() == null || userRegisterRequest.getConfirmPassword().trim().isEmpty()) {
            throw new UserRegisterExceptions("confirm password is required pls input confirm password");
        }
        if (!userRegisterRequest.getPassword().equals(userRegisterRequest.getConfirmPassword())) {
            throw new UserRegisterExceptions("password does not match confirm password");
        }
        if(userRegisterRequest.getPhoneNumber().length()!=11){
            throw new UserRegisterExceptions("phone number must be 11 digit");
        }
        Optional<User>userOptional = userRepository.findByUserName(userRegisterRequest.getUserName());
        if(userOptional.isPresent()){
            throw new UserRegisterExceptions("username already exists");
        }
        Optional<User>duplicateFirstNameAndLastName = userRepository.findByFirstNameAndLastName(userRegisterRequest.getFirstName(), userRegisterRequest.getLastName());
        if(duplicateFirstNameAndLastName.isPresent()){
            throw new UserRegisterExceptions("first name already exists and last name already exists");
        }

    }

    @Override
    public UserLoginResponse loginUser(UserLoginRequest userLoginRequest) {
        if (userLoginRequest.getUsername() == null || userLoginRequest.getUsername().trim().isEmpty()) {
            throw new UserRegisterExceptions("Username is empty, please input username");
        }
        if (userLoginRequest.getPassword() == null || userLoginRequest.getPassword().trim().isEmpty()) {
            throw new UserRegisterExceptions("Password is empty, please input password");
        }

        Optional<User> userOptional = userRepository.findByUserName(userLoginRequest.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(user.isDisable()){
                throw new UserRegisterExceptions("your account is disabled pls inform our customer account");
            }
            if (passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
                user.setLogin(true);
                userRepository.save(user);

                UserLoginResponse response = new UserLoginResponse();
                response.setMessage("Login successful");
                response.setLoginStatus(true);
                return response;
            } else {
                throw new UserRegisterExceptions("Invalid password");
            }
        } else {
            throw new UserRegisterExceptions("Username not found");
        }
    }

    @Override
    public UserResetPasswordResponse resetPassword(UserResetPasswordRequest userResetPasswordRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(userResetPasswordRequest.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(userResetPasswordRequest.getPassword()));
            userRepository.save(user);
            UserResetPasswordResponse response = new UserResetPasswordResponse();
            response.setMessage("Your password has been successfully reset");
            return response;
        }
        throw new UserRegisterExceptions("user not found");
    }
}

