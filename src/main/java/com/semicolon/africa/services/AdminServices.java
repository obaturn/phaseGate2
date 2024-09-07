package com.semicolon.africa.services;

import com.semicolon.africa.data.model.User;
import com.semicolon.africa.dto.AdminRequest.AdminLoginRequest;
import com.semicolon.africa.dto.AdminRequest.AdminRegisterRequest;
import com.semicolon.africa.dto.AdminRequest.DeleteUserAccountRequest;
import com.semicolon.africa.dto.AdminRequest.DisableUserAccountRequest;
import com.semicolon.africa.dto.AdminResponse.*;

import java.util.List;

public interface AdminServices {
    AdminRegisterResponse registerAdmin(AdminRegisterRequest adminRegisterRequest);
    AdminLoginResponse loginAdmin(AdminLoginRequest adminLoginRequest);
    AdminDeleteUserResponse deleteUserAccount(DeleteUserAccountRequest deleteUserAccountRequest);
    AdminDisableUserAccountResponse disableUserAccount(DisableUserAccountRequest disableUserAccountRequest);
    List<User> getAllUsersFirstName(String firstName);
    List<User> getAllUsersLastName(String lastName);
    List<User> getAllUsers();
    AdminDeleteUserByIdResponse deleteUserById(String adminId, String userIdentity);
    AdminFindUserByIdResponse getUserById(String userId);
    }


