package com.semicolon.africa.Services;

import com.semicolon.africa.Data.Model.User;
import com.semicolon.africa.Data.Repository.AdminRepository;
import com.semicolon.africa.Data.Repository.UserRepository;
import com.semicolon.africa.Dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class AdminServicesImplementationTest {
    @Autowired
    private AdminServicesImplementation adminServicesImplementation;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserServicesImplementation UserServicesImplementation;
    @Autowired
    private UserServicesImplementation userServicesImplementation;

    @Test
    public void test_That_I_Register_As_Admin() {
        AdminRegisterRequest adminRegisterRequest = new AdminRegisterRequest();
        adminRegisterRequest.setFirstName("tolu");
        adminRegisterRequest.setLastName("tola");
        adminRegisterRequest.setEmail("tola@gmail.com");
        adminRegisterRequest.setHomeAddress("sabo");
        adminRegisterRequest.setPhoneNumber("08190347892");
        adminRegisterRequest.setGender("male");
        AdminRegisterResponse response = adminServicesImplementation.registerAdmin(adminRegisterRequest);
        response.setStatusMessage("you have been appointed");
        response.setFirstName(adminRegisterRequest.getFirstName());
        response.setLastName(adminRegisterRequest.getLastName());
        assertThat(response.getFirstName()).isEqualTo(adminRegisterRequest.getFirstName());
        assertThat(response.getLastName()).isEqualTo(adminRegisterRequest.getLastName());
        assertThat(response.getStatusMessage()).isEqualTo("you have been appointed");
    }

    @Test
    public void test_That_Account_Is_Disable_By_Admin() {
        AdminRegisterRequest adminRegisterRequest = new AdminRegisterRequest();
        adminRegisterRequest.setFirstName("joshua");
        adminRegisterRequest.setLastName("ola");
        adminRegisterRequest.setEmail("oban@gmail.com");
        adminRegisterRequest.setHomeAddress("ajah");
        adminRegisterRequest.setPhoneNumber("08090347892");
        adminRegisterRequest.setGender("male");
        AdminRegisterResponse response = adminServicesImplementation.registerAdmin(adminRegisterRequest);
        response.setStatusMessage("you have been appointed");
        response.setFirstName(adminRegisterRequest.getFirstName());
        response.setLastName(adminRegisterRequest.getLastName());
        assertThat(response.getFirstName()).isEqualTo(adminRegisterRequest.getFirstName());
        assertThat(response.getLastName()).isEqualTo(adminRegisterRequest.getLastName());
        assertThat(response.getStatusMessage()).isEqualTo("you have been appointed");

        User user = new User();
        user.setFirstName("jo");
        user.setLastName("olami");
        user.setEmail("oban@gmail.com");
        user.setAddress("sabo");
        user.setPhoneNumber("08090347894");
        user.setGender("male");
        userRepository.save(user);


        AdminDisableAccountRequest request = new AdminDisableAccountRequest();
        request.setPhoneNumber("08090347892");
        request.setEmail("oban@gmail.com");
        AdminDisableAccountResponse disableAccountResponse = adminServicesImplementation.accountDisableAdmin(request);
        assertThat(disableAccountResponse.getMessage()).isEqualTo("account disable and blocked successfully");
    }

}

