package com.semicolon.africa.services;

import com.semicolon.africa.data.repository.UserRepository;
import com.semicolon.africa.dto.UserRegisterRequest;
import com.semicolon.africa.dto.UserRegisterResponse;
import com.semicolon.africa.exceptions.UserRegisterExceptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserServicesImplementationTest {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  UserServices userServices;
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
}