package com.semicolon.africa.services;

import com.semicolon.africa.dto.UserRequest.*;
import com.semicolon.africa.dto.UserResponse.*;

public interface UserServices {
    UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest);
    UserLoginResponse loginUser(UserLoginRequest userLoginRequest);
    UserResetPasswordResponse resetPassword(UserResetPasswordRequest userResetPasswordRequest);
    UserStoreResponse storeUserImportantFiles(UserStoreRequest userStoreRequest);
    UserSavedPasswordResponse saveUserPassword(UserSavedPasswordRequest userSavedPasswordRequest);

}
