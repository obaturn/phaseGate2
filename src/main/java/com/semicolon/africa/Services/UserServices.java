package com.semicolon.africa.Services;

import com.semicolon.africa.Dto.*;

public interface UserServices {
    UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest);
    UserLoginResponse UserLogin (UserLoginRequest login );
    UserLogOutResponse userLogOut(UserLogOutRequest userLogOutRequest);
    UserCreatePasswordResponse createPassword(UserCreatePasswordRequest userCreatePasswordRequest);
}
