package com.semicolon.africa.Services;

import com.semicolon.africa.Data.Model.User;
import com.semicolon.africa.Data.Repository.UserRepository;
import com.semicolon.africa.Dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserServicesImplementationTest {
   @Autowired
   private UserRepository UserRepository;
   @Autowired
   private UserServicesImplementation userServicesImplementation;


    @Test
    public  void  testThatICanRegisterAUser(){
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setFirstName("Ivan");
        registerRequest.setLastName("Ivanov");
        registerRequest.setEmail("ivanov@gmail.com");
        registerRequest.setPassword("password");
        registerRequest.setPhoneNumber("08104375142");
        registerRequest.setAddress("sabo");
        registerRequest.setUserName("obaturn");
        UserRegisterResponse response = userServicesImplementation.registerUser(registerRequest);
        response.setMessage("successfully registered");
        assertThat(response.getMessage()).isEqualTo("successfully registered");

   }
   @Test
   public void testThatMyUserCanLogin(){
       UserRegisterRequest registerRequest = new UserRegisterRequest();
       registerRequest.setFirstName("Ivan");
       registerRequest.setLastName("Ivanov");
       registerRequest.setEmail("ivanova@gmail.com");
       registerRequest.setPassword("password");
       registerRequest.setPhoneNumber("08104375142");
       registerRequest.setAddress("sabo");
       registerRequest.setUserName("obaturn");
       UserRegisterResponse response = userServicesImplementation.registerUser(registerRequest);
       response.setMessage("successfully registered");
       assertThat(response.getMessage()).isEqualTo("successfully registered");

       UserLoginRequest loginRequest = new UserLoginRequest();
       loginRequest.setEmail("ivanova@gmail.com");
       loginRequest.setPassword("password");
       loginRequest.setUserName("obaturn");
       UserLoginResponse response1 = userServicesImplementation.UserLogin(loginRequest);
       response1.setMessage("login successful");
       assertThat(response1.getMessage()).isEqualTo("login successful");


   }
   @Test
    public void testThatMyUserCanLogOut(){
       UserRegisterRequest registerRequest = new UserRegisterRequest();
       registerRequest.setFirstName("tol");
       registerRequest.setLastName("sam");
       registerRequest.setEmail("ival@gmail.com");
       registerRequest.setPassword("12345679");
       registerRequest.setPhoneNumber("08104375149");
       registerRequest.setAddress("saboYaba");
       registerRequest.setUserName("oba");
       UserRegisterResponse response = userServicesImplementation.registerUser(registerRequest);
       response.setMessage("successfully registered");
       assertThat(response.getMessage()).isEqualTo("successfully registered");

       UserLoginRequest loginRequest = new UserLoginRequest();
       loginRequest.setEmail("ival@gmail.com");
       loginRequest.setPassword("12345679");
       loginRequest.setUserName("oba");
       UserLoginResponse response1 = userServicesImplementation.UserLogin(loginRequest);
       response1.setMessage("login successful");
       assertThat(response1.getMessage()).isEqualTo("login successful");
       UserLogOutRequest logoutRequest = new UserLogOutRequest();
       logoutRequest.setUserName("oba");
       UserLogOutResponse logOutResponse = userServicesImplementation.userLogOut(logoutRequest);
       logOutResponse.setMessage("user logout successful");
       assertThat(logOutResponse.getMessage()).isEqualTo("user logout successful");

   }
   @Test
    public  void testThatMyUserCanCreateAPassword(){
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setFirstName("Ibro");
        registerRequest.setLastName("Ibrahim");
        registerRequest.setEmail("ako@gmail.com");
        registerRequest.setPassword("1234567");
        UserRegisterResponse response = userServicesImplementation.registerUser(registerRequest);
        response.setMessage("successfully registered");
        assertThat(response.getMessage()).isEqualTo("successfully registered");
        UserCreatePasswordRequest passwordRequest = new UserCreatePasswordRequest();
        passwordRequest.setPassword("1234567");
        UserCreatePasswordResponse passwordResponse = userServicesImplementation.createPassword(passwordRequest);
        passwordResponse.setMessage("password created successfully");
        assertThat(passwordResponse.getMessage()).isEqualTo("password created successfully");

   }


}