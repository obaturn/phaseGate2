package com.semicolon.africa.services;

import com.semicolon.africa.dto.AdminLoginRequest;
import com.semicolon.africa.dto.AdminLoginResponse;
import com.semicolon.africa.dto.AdminRegisterRequest;
import com.semicolon.africa.dto.AdminRegisterResponse;

public interface AdminServices {
    AdminRegisterResponse registerAdmin(AdminRegisterRequest adminRegisterRequest);
    AdminLoginResponse loginAdmin(AdminLoginRequest adminLoginRequest);
}
