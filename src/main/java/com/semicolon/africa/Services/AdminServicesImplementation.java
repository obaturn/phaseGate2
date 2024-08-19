package com.semicolon.africa.Services;

import com.semicolon.africa.Data.Model.Admin;
import com.semicolon.africa.Data.Model.User;
import com.semicolon.africa.Data.Repository.AdminRepository;
import com.semicolon.africa.Data.Repository.UserRepository;
import com.semicolon.africa.Dto.*;
import com.semicolon.africa.Exceptions.AdminExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServicesImplementation implements AdminServices {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public AdminRegisterResponse registerAdmin(AdminRegisterRequest registerRequest) {
        Admin admin = new Admin();
        admin.setFirstName(registerRequest.getFirstName());
        admin.setLastName(registerRequest.getLastName());
        admin.setEmail(registerRequest.getEmail());
        admin.setHomeAddress(registerRequest.getHomeAddress());
        admin.setPhoneNumber(registerRequest.getPhoneNumber());
        admin.setGender(registerRequest.getGender());
        adminRepository.save(admin);
        AdminRegisterResponse adminRegisterResponse = new AdminRegisterResponse();
        adminRegisterResponse.setMessage("Admin registered successfully");
        adminRegisterResponse.setFirstName(registerRequest.getFirstName());
        adminRegisterResponse.setLastName(registerRequest.getLastName());
        adminRegisterResponse.setStatusMessage("you have been appointed");
        return adminRegisterResponse;
    }

    @Override
    public AdminDisableAccountResponse accountDisableAdmin(AdminDisableAccountRequest adminDisableAccountRequest){
        Optional<Admin> admin = adminRepository.findByEmail(adminDisableAccountRequest.getEmail());
        if (admin.isPresent()) {
            Optional<User> user = userRepository.findByEmail(adminDisableAccountRequest.getEmail());
            if (user.isPresent()) {
                userRepository.delete(user.get());
                AdminDisableAccountResponse adminDisableAccountResponse = new AdminDisableAccountResponse();
                adminDisableAccountResponse.setMessage("account disable and blocked successfully");
                return adminDisableAccountResponse;
            } else {
                throw new AdminExceptions("User not found");
            }
        } else {
            throw new AdminExceptions("Admin not found");
        }
    }}





