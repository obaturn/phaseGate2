package com.semicolon.africa.services;

import com.semicolon.africa.data.model.Admin;
import com.semicolon.africa.data.model.User;
import com.semicolon.africa.data.repository.AdminRepository;
import com.semicolon.africa.data.repository.UserRepository;
import com.semicolon.africa.dto.AdminRequest.AdminLoginRequest;
import com.semicolon.africa.dto.AdminRequest.AdminRegisterRequest;
import com.semicolon.africa.dto.AdminRequest.DeleteUserAccountRequest;
import com.semicolon.africa.dto.AdminRequest.DisableUserAccountRequest;
import com.semicolon.africa.dto.AdminResponse.*;
import com.semicolon.africa.exceptions.AdminExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServicesImplementation implements AdminServices {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public AdminRegisterResponse registerAdmin(AdminRegisterRequest adminRegisterRequest) {
        try{
            validateAdmin(adminRegisterRequest);
        }catch (AdminExceptions e){
            throw new AdminExceptions(e.getMessage());
        }
        Admin admin = new Admin();
        admin.setFirstName(adminRegisterRequest.getFirstName());
        admin.setLastName(adminRegisterRequest.getLastName());
        admin.setEmail(adminRegisterRequest.getEmail());
        admin.setPhoneNumber(adminRegisterRequest.getPhoneNumber());
        admin.setGender(adminRegisterRequest.getGender());
        admin.setHomeAddress(adminRegisterRequest.getHomeAddress());
        admin.setPassword(passwordEncoder.encode(adminRegisterRequest.getPassword()));
        admin.setConfirmPassword(passwordEncoder.encode(adminRegisterRequest.getConfirmPassword()));
        admin.setConfirmedEmail(adminRegisterRequest.getConfirmedEmail());
        admin.setUserName(adminRegisterRequest.getUserName());
        adminRepository.save(admin);
        System.out.println("Admin saved: " + admin);
        AdminRegisterResponse adminRegisterResponse = new AdminRegisterResponse();
        adminRegisterResponse.setMessage("you have successfully registered as an admin");
        return adminRegisterResponse;
    }

    @Override
    public AdminLoginResponse loginAdmin(AdminLoginRequest adminLoginRequest) {

        if(adminLoginRequest.getUserName() == null || adminLoginRequest.getPassword() == null){
            throw new AdminExceptions("Username or password is missing, please provide both.");
        }
        if(adminLoginRequest.getPassword().trim().isEmpty()){
            throw new AdminExceptions("Password is empty, please enter a password.");
        }
        if(adminLoginRequest.getUserName().trim().isEmpty()){
            throw new AdminExceptions("Username is empty, please enter a username.");
        }
        Optional<Admin> adminOptional = adminRepository.findByUserName(adminLoginRequest.getUserName());
        if (adminOptional.isEmpty()) {

            throw new AdminExceptions("Admin with the given username does not exist.");
        }

        Admin admin = adminOptional.get();
        admin.setUserName(adminLoginRequest.getUserName());
        admin.setPassword(passwordEncoder.encode(adminLoginRequest.getPassword()));

        if (!passwordEncoder.matches(adminLoginRequest.getPassword(), admin.getPassword())) {

            throw new AdminExceptions("Incorrect password.");
        }
        admin.setLogin(true);
        adminRepository.save(admin);

        AdminLoginResponse adminLoginResponse = new AdminLoginResponse();
        adminLoginResponse.setMessage("You have successfully logged in.");
        return adminLoginResponse;
    }

    @Override
    public AdminDeleteUserResponse deleteUserAccount(DeleteUserAccountRequest request) {
       Optional<Admin> adminOptional = adminRepository.findByEmail(request.getAdminEmail());
        if(adminOptional.isEmpty()){
            throw new AdminExceptions("Admin with the given id does not exist.");
        }

        Optional<User> userOptional = userRepository.findByUserName(request.getUsername());
        if(userOptional.isEmpty()){
            throw new AdminExceptions("User with the given identity does not exist.");
        }
        if (request.getUsername() == null) {
            throw new IllegalArgumentException("User identity must not be null");
        }

        User user = userOptional.get();
        userRepository.delete(user);
        AdminDeleteUserResponse adminDeleteUserResponse = new AdminDeleteUserResponse();
        adminDeleteUserResponse.setMessage("You have successfully deleted the user with the given userName \"" + user.getUserName());
        adminDeleteUserResponse.setTimeStamp(LocalDateTime.now().toString());
        adminDeleteUserResponse.setStatus("200 , SUCCESS");

        return adminDeleteUserResponse;
    }

    @Override
    public AdminDisableUserAccountResponse disableUserAccount(DisableUserAccountRequest disableUserAccountRequest) {
        Optional<Admin> adminOpt = adminRepository.findByEmail(disableUserAccountRequest.getEmail());
        if (adminOpt.isEmpty()) {
                throw new AdminExceptions("Admin with the given id does not exist.");
        }
        Optional<User> optionalUser = userRepository.findByUserName(disableUserAccountRequest.getUsername());
        if (optionalUser.isEmpty()) {
            throw new AdminExceptions("User not found:");
        }
        User user = optionalUser.get();
        user.setDisable(true);
        userRepository.save(user);

        AdminDisableUserAccountResponse adminDisableUserAccountResponse = new AdminDisableUserAccountResponse();
        adminDisableUserAccountResponse.setMessage("You have successfully disabled user account.");
        adminDisableUserAccountResponse.setTimeStamp(LocalDateTime.now().toString());
        adminDisableUserAccountResponse.setStatus("200 , SUCCESS");
        return  adminDisableUserAccountResponse;
    }

    @Override
    public List<User> getAllUsersFirstName(String firstName) {
        System.out.println("firstName: " + firstName);
            return userRepository.findByFirstName(firstName);
    }

    @Override
    public List<User> getAllUsersLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public AdminDeleteUserByIdResponse deleteUserById(String adminId, String userIdentity) {
        Optional<Admin> adminOptional = adminRepository.findById(adminId);
        if (adminOptional.isEmpty()) {
            throw new AdminExceptions("Admin with the given id does not exist.");
        }
        Optional<User> userOptional = userRepository.findById(userIdentity);
        if (userOptional.isEmpty()) {
            throw new AdminExceptions("User not found: " + userIdentity);
        }
        User user = userOptional.get();
        userRepository.delete(user);
        AdminDeleteUserByIdResponse adminDeleteUserByIdResponse = new AdminDeleteUserByIdResponse();
        adminDeleteUserByIdResponse.setMessage("You have successfully deleted user with id \"" + user.getId() + "\"");
        adminDeleteUserByIdResponse.setTimestamp(LocalDateTime.now());
        return adminDeleteUserByIdResponse;
    }

    @Override
    public AdminFindUserByIdResponse getUserById(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new AdminExceptions("User not found: " + userId);
        }
        AdminFindUserByIdResponse response = new AdminFindUserByIdResponse();
        response.setMessage("User with id \"" + userId);
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus("200 , SUCCESS");
        return response;
    }


    private void validateAdmin(AdminRegisterRequest adminRegisterRequest) {
        if(adminRegisterRequest.getPhoneNumber() == null || adminRegisterRequest.getPhoneNumber().trim().isEmpty()){
            throw new AdminExceptions("phoneNumber cannot be empty pls input phone number");
        }
        if (adminRegisterRequest.getEmail() == null || adminRegisterRequest.getEmail().trim().isEmpty() || !adminRegisterRequest.getEmail().matches(".*@.*")) {
            throw new AdminExceptions("Email cannot be empty and must contain '@'");
        }

        if(adminRegisterRequest.getFirstName() == null || adminRegisterRequest.getFirstName().trim().isEmpty()){
            throw new AdminExceptions("firstName cannot be empty pls input first name");
        }
        if(adminRegisterRequest.getLastName() == null || adminRegisterRequest.getLastName().trim().isEmpty()){
            throw new AdminExceptions("lastName cannot be empty pls input last name");
        }
        if(adminRegisterRequest.getPassword() == null || adminRegisterRequest.getPassword().trim().isEmpty()){
            throw new AdminExceptions("password cannot be empty pls input password");
        }
        if(!adminRegisterRequest.getPassword().equals(adminRegisterRequest.getConfirmPassword())){
            throw new AdminExceptions("password does not match confirmPassword");
        }
        if(adminRegisterRequest.getPhoneNumber().length() != 11){
            throw new AdminExceptions("phoneNumber must be exactly 11 digits");
        }

        if(adminRegisterRequest.getGender() == null || adminRegisterRequest.getGender().trim().isEmpty()){
            throw new AdminExceptions("gender cannot be empty pls input gender");
        }
        if(adminRegisterRequest.getHomeAddress() == null || adminRegisterRequest.getHomeAddress().trim().isEmpty()){
            throw new AdminExceptions("homeAddress cannot be empty pls input home address");
        }
        if(adminRegisterRequest.getUserName() == null || adminRegisterRequest.getUserName().trim().isEmpty()){
            throw new AdminExceptions("userName cannot be empty pls input user name");
        }
        if (adminRegisterRequest.getConfirmedEmail() == null || adminRegisterRequest.getConfirmedEmail().trim().isEmpty() || !adminRegisterRequest.getConfirmedEmail().matches(".*@."))  {
            throw new AdminExceptions("confirmedEmail cannot be empty pls input confirmed email");
        }
        if(!adminRegisterRequest.getPhoneNumber().matches("//d+")){
            throw new AdminExceptions("phoneNumber must start with positive Numbers");

        }
        if(!adminRegisterRequest.getConfirmedEmail().equals(adminRegisterRequest.getEmail())){
            throw new AdminExceptions("confirmed email does not match email pls input the correct own");
        }
        Optional<Admin> adminOptional = adminRepository.findByUserName(adminRegisterRequest.getUserName());
        if(adminOptional.isPresent()){
            throw new AdminExceptions("Username already exists.");
        }
        Optional<Admin> adminOptional2 = adminRepository.findByPhoneNumber(adminRegisterRequest.getPhoneNumber());
        if(adminOptional2.isPresent()){
            throw new AdminExceptions("Phone number already exists.");
        }
        Optional<Admin> adminOptional3 = adminRepository.findByEmail(adminRegisterRequest.getEmail());
        if(adminOptional3.isPresent()){
            throw new AdminExceptions("Email already exists.");
        }
    }

}
