package com.semicolon.africa.Controller;

import com.semicolon.africa.dto.AdminResponse.ApiResponse;
import com.semicolon.africa.dto.UserRequest.*;
import com.semicolon.africa.dto.UserResponse.*;
import com.semicolon.africa.services.UserServicesImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/User")
public class UserController {
    @Autowired
    private UserServicesImplementation userService;
    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        try{
          UserRegisterResponse response= userService.registerUser(userRegisterRequest);
          return new ResponseEntity<>(new ApiResponse(true, response),CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_GATEWAY);
        }
    }
    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
        try{
            UserLoginResponse response= userService.loginUser(userLoginRequest);
            return new ResponseEntity<>(new ApiResponse(true, response),CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_GATEWAY);
        }
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody UserResetPasswordRequest userResetPasswordRequest) {
        try{
            UserResetPasswordResponse response =userService.resetPassword(userResetPasswordRequest);
            return new ResponseEntity<>(new ApiResponse(true, response),CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_GATEWAY);
        }
    }
    @PostMapping("/storeImportantFiles")
    public ResponseEntity<?> storeImportantFiles(@RequestBody UserStoreRequest userStoreRequest) {
        try{
            UserStoreResponse response = userService.storeUserImportantFiles(userStoreRequest);
            return new ResponseEntity<>(new ApiResponse(true, response),CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_GATEWAY);
        }
    }
    @PostMapping("/saveUserPassword")
    public ResponseEntity<?> saveUserPassword(@RequestBody UserSavedPasswordRequest userSavedPasswordRequest){
        try{
            UserSavedPasswordResponse response =userService.saveUserPassword(userSavedPasswordRequest);
            return new ResponseEntity<>(new ApiResponse(true, response),CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_GATEWAY);
        }
    }
    @PostMapping("/retrievePassword")
    public ResponseEntity<?> retrievePassword(@RequestBody UserRetrievePasswordRequest userRetrievePasswordRequest){
        try{
            UserRetrievePasswordResponse response =userService.retrievePassword(userRetrievePasswordRequest);
            return new ResponseEntity<>(new ApiResponse(true, response),CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_GATEWAY);
        }
    }

}
