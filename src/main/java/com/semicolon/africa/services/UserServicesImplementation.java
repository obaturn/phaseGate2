package com.semicolon.africa.services;

import com.semicolon.africa.Utilities.MyMethodEncryption;
import com.semicolon.africa.data.model.User;
import com.semicolon.africa.data.repository.UserRepository;
import com.semicolon.africa.dto.UserRequest.*;
import com.semicolon.africa.dto.UserResponse.*;
import com.semicolon.africa.exceptions.UserExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        }catch (UserExceptions e){
            throw new UserExceptions(e.getMessage());
        }
        User user = new User();
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(MyMethodEncryption.encrypt(userRegisterRequest.getPassword()));
        user.setPhoneNumber(userRegisterRequest.getPhoneNumber());
        user.setGender(userRegisterRequest.getGender());
        user.setUserName(userRegisterRequest.getUserName());
        user.setAddress(userRegisterRequest.getAddress());
        user.setConfirmPassword(MyMethodEncryption.encrypt(userRegisterRequest.getConfirmPassword()));
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
            throw new UserExceptions("first name is required pls input firstName");

        }
        if (userRegisterRequest.getLastName() == null || userRegisterRequest.getLastName().trim().isEmpty()) {
            throw new UserExceptions("last name is required pls input lastName");
        }
        if (userRegisterRequest.getEmail() == null || userRegisterRequest.getEmail().trim().isEmpty()) {
            throw new UserExceptions("Email is required and must contain annotation of '@'");
        }

        if (userRegisterRequest.getPhoneNumber() == null || userRegisterRequest.getPhoneNumber().trim().isEmpty()) {
            throw new UserExceptions("phone number is required pls input phone number");
        }
        if (userRegisterRequest.getPassword() == null || userRegisterRequest.getPassword().trim().isEmpty()) {
            throw new UserExceptions("password is required pls input password");
        }
        if (userRegisterRequest.getConfirmPassword() == null || userRegisterRequest.getConfirmPassword().trim().isEmpty()) {
            throw new UserExceptions("confirm password is required pls input confirm password");
        }
        if (!userRegisterRequest.getPassword().equals(userRegisterRequest.getConfirmPassword())) {
            throw new UserExceptions("password does not match confirm password");
        }
        String encryptedPassword = MyMethodEncryption.encrypt(userRegisterRequest.getPassword());
        String encryptedConfirmPassword = MyMethodEncryption.encrypt(userRegisterRequest.getConfirmPassword());
        if(!encryptedPassword.equals(encryptedConfirmPassword)) {
            throw new UserExceptions("password does not match confirm password");
        }
        if(!userRegisterRequest.getPhoneNumber().matches("\\d+")){
            throw new UserExceptions("phone number is not valid pls input a positive Number");

        }
        if(userRegisterRequest.getPhoneNumber().length()!=11){
            throw new UserExceptions("phone number must be exactly 11 digit not less than 11 digit or more than 11 digit");
        }
        Optional<User>userOptional = userRepository.findByUserName(userRegisterRequest.getUserName());
        if(userOptional.isPresent()){
            throw new UserExceptions("username already exists");
        }
        Optional<User>duplicateFirstNameAndLastName = userRepository.findByFirstNameAndLastName(userRegisterRequest.getFirstName(), userRegisterRequest.getLastName());
        if(duplicateFirstNameAndLastName.isPresent()){
            throw new UserExceptions("first name already exists and last name already exists");
        }
        Optional<User>duplicateEmail = userRepository.findByEmail(userRegisterRequest.getEmail());
        if(duplicateEmail.isPresent()){
            throw new UserExceptions("email already exists");
        }
        Optional<User>duplicatePhoneNumber = userRepository.findByPhoneNumber(userRegisterRequest.getPhoneNumber());
        if(duplicatePhoneNumber.isPresent()){
            throw new UserExceptions("phone number already exists");
        }
    }

    @Override
    public UserLoginResponse loginUser(UserLoginRequest userLoginRequest) {
        if (userLoginRequest.getUsername() == null || userLoginRequest.getUsername().trim().isEmpty()) {
            throw new UserExceptions("Username is empty, please input username");
        }
        if (userLoginRequest.getPassword() == null || userLoginRequest.getPassword().trim().isEmpty()) {
            throw new UserExceptions("Password is empty, please input password");
        }

        Optional<User> userOptional = userRepository.findByUserName(userLoginRequest.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(user.isDisable()){
                throw new UserExceptions("your account is disabled pls inform our customer account");
            }
            String decryptedPassword = MyMethodEncryption.decrypt(user.getPassword());
            if (!decryptedPassword.equals(userLoginRequest.getPassword())) {

                throw new UserExceptions("password does not match confirm password");
            }
                user.setLogin(true);
                userRepository.save(user);

                UserLoginResponse response = new UserLoginResponse();
                response.setMessage("Login successful");
                response.setLoginStatus(true);
                return response;
            } else {
                throw new UserExceptions("Username not found");
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
        throw new UserExceptions("user not found");
    }


    @Override
    public UserStoreResponse storeUserImportantFiles(UserStoreRequest userStoreRequest) {

        Optional<User> userOptional = userRepository.findByEmail(userStoreRequest.getEmail().toLowerCase());

        if (userOptional.isPresent()) {

            User user = userOptional.get();
            user.setTittle(userStoreRequest.getTitle());
            user.setBody(userStoreRequest.getBody());
            user.setRequest(userStoreRequest);

            userRepository.save(user);

            UserStoreResponse response = new UserStoreResponse();
            response.setMessage("Your request has been successfully stored");
            response.setTimestamp(LocalDateTime.now());
            response.setCreated(LocalDateTime.now());
            response.setStatus("200 Created");

            return response;
        } else {

            System.out.println("User not found for email: " + userStoreRequest.getEmail());
            throw new UserExceptions("User not found");
        }
    }

    @Override
    public UserSavedPasswordResponse saveUserPassword(UserSavedPasswordRequest userSavedPasswordRequest) {
        if (userSavedPasswordRequest.getFirstName() == null || userSavedPasswordRequest.getFirstName().trim().isEmpty() ||
                userSavedPasswordRequest.getLastName() == null || userSavedPasswordRequest.getLastName().trim().isEmpty()) {
            throw new UserExceptions("Please input your first name and last name");
        }

        Optional<User> userOptional = userRepository.findByFirstNameAndLastName(userSavedPasswordRequest.getFirstName(), userSavedPasswordRequest.getLastName());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String encryptPassword = MyMethodEncryption.encrypt(userSavedPasswordRequest.getPassword());
            user.setPassword(encryptPassword);
            userRepository.save(user);

            UserSavedPasswordResponse response = new UserSavedPasswordResponse();
            response.setMessage("Your password has been successfully saved");
            response.setDateTime(LocalDateTime.now());
            return response;
        }

        throw new UserExceptions("User not found with the provided first name and last name");
    }


    @Override
    public UserRetrievePasswordResponse retrievePassword(UserRetrievePasswordRequest userRetrievePasswordRequest) {
        if(userRetrievePasswordRequest.getUserName() == null || userRetrievePasswordRequest.getUserName().trim().isEmpty()){
            throw new UserExceptions("Username is empty, please input username");
        }
        if(userRetrievePasswordRequest.getEmail() == null || userRetrievePasswordRequest.getEmail().trim().isEmpty() || !userRetrievePasswordRequest.getEmail().matches(".*@.*")){
            throw new UserExceptions("Email address is empty pls input email with the annotation @ in the email");

        }
        Optional<User> userOptional = userRepository.findByEmail(userRetrievePasswordRequest.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String decryptedPassword = MyMethodEncryption.decrypt(user.getPassword());
            UserRetrievePasswordResponse response = new UserRetrievePasswordResponse();
            response.setMessage("Your have successfully retrieve back your password");
            response.setTimestamp(LocalDateTime.now());
            response.setStatus("SUCCESS");
            response.setPassword(decryptedPassword);
            return response;

        }
        throw  new UserExceptions("user not found with the email and the username provided");
     }
    }

