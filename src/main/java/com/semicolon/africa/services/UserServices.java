package com.semicolon.africa.services;

import com.semicolon.africa.dto.UserLoginRequest;
import com.semicolon.africa.dto.UserLoginResponse;
import com.semicolon.africa.dto.UserRegisterRequest;
import com.semicolon.africa.dto.UserRegisterResponse;

public interface UserServices {
    UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest);
    UserLoginResponse loginUser(UserLoginRequest userLoginRequest);
}
