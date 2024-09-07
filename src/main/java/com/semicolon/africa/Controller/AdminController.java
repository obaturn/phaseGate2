package com.semicolon.africa.Controller;

import com.semicolon.africa.data.model.User;
import com.semicolon.africa.dto.*;
import com.semicolon.africa.services.AdminServicesImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/Admin")
public class AdminController {
    @Autowired
    private AdminServicesImplementation adminService;

    @PostMapping("/registerAdmin")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminRegisterRequest request) {
        try{
            AdminRegisterResponse response = adminService.registerAdmin(request);
            return new ResponseEntity<>(new ApiResponse(true, response),CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),BAD_REQUEST);
        }
    }
    @PostMapping("/loginAdmin")
    public ResponseEntity<?> loginAdmin(@RequestBody AdminLoginRequest request) {
        try{
            AdminLoginResponse response = adminService.loginAdmin(request);
            return new ResponseEntity<>(new ApiResponse(true, response),CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),BAD_REQUEST);
        }
    }
    @DeleteMapping("/deleteUserAccount")
    public ResponseEntity<?> deleteUserAccount(@RequestParam String adminId , @RequestParam String userIdentity){
        try{
           AdminDeleteUserResponse response = adminService.deleteUserAccount(adminId,userIdentity);
           return new ResponseEntity<>(new ApiResponse(true, response),CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),BAD_REQUEST);
        }
    }
    @PatchMapping("/disableUserAccount")
    public ResponseEntity<?> disableUserAccount(@RequestParam String adminId , @RequestParam String userIdentity){
        try{
           AdminDisableUserAccountResponse response= adminService.disableUserAccount(adminId,userIdentity);
           return new ResponseEntity<>(new ApiResponse(true, response),CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),BAD_REQUEST);
        }
    }
    @GetMapping("/getAllUsersFirstName")
    public ResponseEntity<?> getAllUsersFirstName(@RequestParam String firstName){
        try{
            List<User> users = adminService.getAllUsersFirstName(firstName);
            return new ResponseEntity<>(new ApiResponse(true, users),CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),BAD_REQUEST);
        }

    }
    @GetMapping("/getAllUsersLastName")
    public ResponseEntity<?> getAllUsersLastName(@RequestParam String lastName){
        try{
            List<User> users = adminService.getAllUsersLastName(lastName);
            return new ResponseEntity<>(new ApiResponse(true, users),CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),BAD_REQUEST);
        }
    }
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers(){
        try {
            List<User> users = adminService.getAllUsers();
            return new ResponseEntity<>(new ApiResponse(true, users),CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),BAD_REQUEST);
        }
    }
    @DeleteMapping("/deleteUserById")
    public ResponseEntity<?> deleteUserById(@RequestParam String adminId ,@RequestParam String userId){
        try{
           AdminDeleteUserByIdResponse response = adminService.deleteUserById(adminId,userId);
           return new ResponseEntity<>(new ApiResponse(true, response),CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),BAD_REQUEST);
        }
    }
    @GetMapping("/getUserById")
    public ResponseEntity<?> getUserById(@RequestParam String userId){
        try{
            AdminFindUserByIdResponse response = adminService.getUserById(userId);
            return new ResponseEntity<>(new ApiResponse(true, response),CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),BAD_REQUEST);
        }
    }

}
