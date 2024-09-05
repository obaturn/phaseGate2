package com.semicolon.africa.services;

import com.semicolon.africa.dto.*;

public interface UserServices {
    UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest);
    UserLoginResponse loginUser(UserLoginRequest userLoginRequest);
    UserResetPasswordResponse resetPassword(UserResetPasswordRequest userResetPasswordRequest);
}
