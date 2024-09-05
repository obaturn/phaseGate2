package com.semicolon.africa.services;

import com.semicolon.africa.data.model.User;
import com.semicolon.africa.data.repository.UserRepository;
import com.semicolon.africa.dto.*;
import com.semicolon.africa.exceptions.UserRegisterExceptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserServicesImplementationTest {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  UserServices userServices;


    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
@BeforeEach
void setUp(){
    userRepository.deleteAll();
}
    @Test
   public void TestThat_I_registerUser() {
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setFirstName("Catalase");
        registerRequest.setLastName("Doyen");
        registerRequest.setEmail("catalse@doyen.com");
        registerRequest.setPassword("123456");
        registerRequest.setConfirmPassword("123456");
        registerRequest.setPhoneNumber("08145678901");
        registerRequest.setUserName("oba@28");
        registerRequest.setAddress("yaba");
        UserRegisterResponse response = userServices.registerUser(registerRequest);
        assertNotNull(response);
        assertThat(response.getFirstName()).isEqualTo("Catalase");
        assertThat(response.getLastName()).isEqualTo("Doyen");
    }
    @Test
    public void TestThat_DuplicateFirstNameAndLastName_ThrowsException() {
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setFirstName("Catalase");
        registerRequest.setLastName("Doyen");
        registerRequest.setEmail("catalse@doyen.com");
        registerRequest.setPassword("123456");
        registerRequest.setConfirmPassword("123456");
        registerRequest.setPhoneNumber("08145678901");
        registerRequest.setUserName("oba@28");
        registerRequest.setAddress("yaba");

        userServices.registerUser(registerRequest);

        UserRegisterRequest duplicateRequest = new UserRegisterRequest();
        duplicateRequest.setFirstName("Catalase");
        duplicateRequest.setLastName("Doyen");
        duplicateRequest.setEmail("another@user.com");
        duplicateRequest.setPassword("654321");
        duplicateRequest.setConfirmPassword("654321");
        duplicateRequest.setPhoneNumber("08145678902");
        duplicateRequest.setUserName("anotherUser");
        duplicateRequest.setAddress("yaba");

        assertThrows(UserRegisterExceptions.class, () -> {
            userServices.registerUser(duplicateRequest);
        });
    }
    @Test
    public void TestThat_UserCanLogin() {
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setFirstName("Catalase");
        registerRequest.setLastName("Doyen");
        registerRequest.setEmail("catalse@doyen.com");
        registerRequest.setPassword("123456");
        registerRequest.setConfirmPassword("123456");
        registerRequest.setPhoneNumber("08145678901");
        registerRequest.setUserName("oba@28");
        registerRequest.setAddress("yaba");

        userServices.registerUser(registerRequest);

        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setUsername("oba@28");
        loginRequest.setPassword("123456");

        UserLoginResponse loginResponse = userServices.loginUser(loginRequest);
        assertNotNull(loginResponse);
        assertTrue(loginResponse.isLoginStatus());
        assertThat(loginResponse.getMessage()).isEqualTo("Login successful");
    }

    @Test
    public void TestThat_InvalidLoginThrowsException() {
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setUsername("nonexistentUser");
        loginRequest.setPassword("wrongPassword");

        assertThrows(UserRegisterExceptions.class, () -> {
            userServices.loginUser(loginRequest);
        });
    }
    @Test
    public void TestThat_UserCanResetPassword() {

        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setFirstName("Catalase");
        registerRequest.setLastName("Doyen");
        registerRequest.setEmail("catalase@doyen.com");
        registerRequest.setPassword("123456");
        registerRequest.setConfirmPassword("123456");
        registerRequest.setPhoneNumber("08145678901");
        registerRequest.setUserName("oba@28");
        registerRequest.setAddress("yaba");
        userServices.registerUser(registerRequest);

        UserResetPasswordRequest resetRequest = new UserResetPasswordRequest();
        resetRequest.setEmail("catalase@doyen.com");
        resetRequest.setPassword("newPassword");

        UserResetPasswordResponse resetResponse = userServices.resetPassword(resetRequest);
        assertNotNull(resetResponse);
        assertEquals("Your password has been successfully reset", resetResponse.getMessage());


        User user = userRepository.findByEmail("catalase@doyen.com").orElse(null);
        assertNotNull(user);
        assertTrue(passwordEncoder.matches("newPassword", user.getPassword()));
    }
    @Test
    public void TestThat_my_restPassword_Method_could_throw_exception_if_no_User() {
    UserResetPasswordRequest resetRequest = new UserResetPasswordRequest();
    resetRequest.setEmail("nonExistingUser@gmail.com");
    resetRequest.setPassword("123456");
    assertThrows(UserRegisterExceptions.class, () -> {
        userServices.resetPassword(resetRequest);
    });
    }

}