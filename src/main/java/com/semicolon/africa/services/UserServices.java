package com.semicolon.africa.services;


import com.semicolon.africa.dto.UserRegisterRequest;
import com.semicolon.africa.dto.UserView;
import com.semicolon.africa.dto.ViewRequest;
import com.semicolon.africa.dto.userRegisterResponse;

public interface UserServices {
    userRegisterResponse registerUser(UserRegisterRequest userRegisterRequest);
    UserView viewerUser (ViewRequest view );

}
