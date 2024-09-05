package com.semicolon.africa.services;

import com.semicolon.africa.dto.UserRegisterRequest;
import com.semicolon.africa.dto.UserRegisterResponse;

public interface UserServices {
    UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest);
}
