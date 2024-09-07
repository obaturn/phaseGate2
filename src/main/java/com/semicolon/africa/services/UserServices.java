package com.semicolon.africa.services;

import com.semicolon.africa.dto.UserRequest.UserLoginRequest;
import com.semicolon.africa.dto.UserRequest.UserRegisterRequest;
import com.semicolon.africa.dto.UserRequest.UserResetPasswordRequest;
import com.semicolon.africa.dto.UserRequest.UserStoreRequest;
import com.semicolon.africa.dto.UserResponse.UserLoginResponse;
import com.semicolon.africa.dto.UserResponse.UserRegisterResponse;
import com.semicolon.africa.dto.UserResponse.UserResetPasswordResponse;
import com.semicolon.africa.dto.UserResponse.UserStoreResponse;

public interface UserServices {
    UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest);
    UserLoginResponse loginUser(UserLoginRequest userLoginRequest);
    UserResetPasswordResponse resetPassword(UserResetPasswordRequest userResetPasswordRequest);
    UserStoreResponse storeUserImportantFiles(UserStoreRequest userStoreRequest);

}
