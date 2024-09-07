package com.semicolon.africa.services;

import com.semicolon.africa.data.model.User;
import com.semicolon.africa.dto.*;

import java.util.List;

public interface AdminServices {
    AdminRegisterResponse registerAdmin(AdminRegisterRequest adminRegisterRequest);
    AdminLoginResponse loginAdmin(AdminLoginRequest adminLoginRequest);
    AdminDeleteUserResponse deleteUserAccount(String adminId, String userIdentity);
    AdminDisableUserAccountResponse disableUserAccount(String adminId, String userIdentity);
    List<User> getAllUsersFirstName(String firstName);
    List<User> getAllUsersLastName(String lastName);
    List<User> gatAllUsers();
    }


