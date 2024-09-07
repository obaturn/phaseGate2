package com.semicolon.africa.services;

import com.semicolon.africa.data.model.User;
import com.semicolon.africa.data.repository.UserRepository;
import com.semicolon.africa.dto.UserRequest.UserLoginRequest;
import com.semicolon.africa.dto.UserRequest.UserRegisterRequest;
import com.semicolon.africa.dto.UserRequest.UserResetPasswordRequest;
import com.semicolon.africa.dto.UserRequest.UserStoreRequest;
import com.semicolon.africa.dto.UserResponse.UserLoginResponse;
import com.semicolon.africa.dto.UserResponse.UserRegisterResponse;
import com.semicolon.africa.dto.UserResponse.UserResetPasswordResponse;
import com.semicolon.africa.dto.UserResponse.UserStoreResponse;
import com.semicolon.africa.exceptions.UserExceptions;
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

        assertThrows(UserExceptions.class, () -> {
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

        assertThrows(UserExceptions.class, () -> {
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
    assertThrows(UserExceptions.class, () -> {
        userServices.resetPassword(resetRequest);
    });
    }
    @Test
    public void TestThat_User_Can_Store_Some_Important_Files() {

        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setFirstName("Catalase");
        registerRequest.setLastName("Doyen");
        registerRequest.setEmail("catal@doyen.com");
        registerRequest.setPassword("1234");
        registerRequest.setConfirmPassword("1234");
        registerRequest.setPhoneNumber("08149678901");
        registerRequest.setUserName("obar@28");
        registerRequest.setAddress("yabar");

        userServices.registerUser(registerRequest);

        UserStoreRequest storeRequest = new UserStoreRequest();
        storeRequest.setEmail("catal@doyen.com");
        storeRequest.setBody("my new files");
        storeRequest.setTitle("The Header");

        UserStoreResponse response = userServices.storeUserImportantFiles(storeRequest);

        assertNotNull(response);
        assertThat(response.getMessage()).isEqualTo("Your request has been successfully stored");
        assertNotNull(response.getCreated());
        assertNotNull(response.getTimestamp());

        User storedUser = userRepository.findByEmail("catal@doyen.com").orElse(null);
        assertNotNull(storedUser);
        assertNotNull(storedUser.getRequest());
    }

    @Test
    public void TestThat_StoreImportantFilesThrowsException_WhenUserNotFound() {

        UserStoreRequest storeRequest = new UserStoreRequest();
        storeRequest.setEmail("nonexistent@user.com");
        storeRequest.setTitle("Important Document");
        storeRequest.setBody("This is an important document.");


        UserExceptions exception = assertThrows(UserExceptions.class, () -> {
            userServices.storeUserImportantFiles(storeRequest);
        });

        assertEquals("User not found", exception.getMessage());
    }

}