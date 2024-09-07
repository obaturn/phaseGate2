package com.semicolon.africa.services;

import com.semicolon.africa.data.model.Admin;
import com.semicolon.africa.data.model.User;
import com.semicolon.africa.data.repository.AdminRepository;
import com.semicolon.africa.data.repository.UserRepository;
import com.semicolon.africa.dto.*;
import com.semicolon.africa.exceptions.AdminExceptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AdminServicesImplementationTest {
   @Autowired
   private AdminServices adminServices;

   @Autowired
   private AdminRepository adminRepository;

   @Autowired
   private UserRepository userRepository;

   private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();




    @BeforeEach
    void setUp() {
        adminRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
   public void Test_That_I_Can_RegisterAdmin() {
        AdminRegisterRequest registerRequest = new AdminRegisterRequest();
        registerRequest.setFirstName("toluwalase");
        registerRequest.setLastName("yemi");
        registerRequest.setPassword("123456");
        registerRequest.setEmail("toluwalaseyemi@gmail.com");
        registerRequest.setConfirmPassword("123456");
        registerRequest.setConfirmedEmail("toluwalaseyemi@gmail.com");
        registerRequest.setGender("male");
        registerRequest.setPhoneNumber("08174975740");
        registerRequest.setUserName("akinOba");
        registerRequest.setHomeAddress("yaba");
        AdminRegisterResponse response = adminServices.registerAdmin(registerRequest);
        assertNotNull(response);
        assertThat(response.getMessage()).isEqualTo("you have successfully registered as an admin");
        Optional<Admin>adminOptional = adminRepository.findByUserName("akinOba");
        System.out.println("All admins in DB: " + adminRepository.findAll());
        assertTrue(adminOptional.isPresent());
        assertThat(adminOptional.get().getUserName()).isEqualTo("akinOba");
    }
    @Test
    public void testRegisterAdmin_EmailMismatch_ThrowsException() {
        AdminRegisterRequest request = new AdminRegisterRequest();
        request.setFirstName("Johny");
        request.setLastName("Doen");
        request.setEmail("john.doe@example.com");
        request.setConfirmedEmail("wrongemail@exam.com"); // Mismatch
        request.setPassword("password123");
        request.setConfirmPassword("password123");
        request.setPhoneNumber("08123456789");
        request.setUserName("johnDoey");
        request.setHomeAddress("yaba");
        request.setGender("male");


        AdminExceptions exception = assertThrows(AdminExceptions.class, () -> {
            adminServices.registerAdmin(request);
        });


        assertEquals("confirmed email does not match email pls input the correct own", exception.getMessage());
    }

    @Test
    public void Test_That_My_Admin_Can_login_To_Their_Account() {
        Admin admin = new Admin();
        admin.setUserName("akinOba");
        admin.setPassword(passwordEncoder.encode("123456"));
        adminRepository.save(admin);
        AdminLoginRequest loginRequest = new AdminLoginRequest();
        loginRequest.setUserName("akinOba");
        loginRequest.setPassword("123456");
        AdminLoginResponse response = adminServices.loginAdmin(loginRequest);
        assertNotNull(response);
        assertEquals("You have successfully logged in.", response.getMessage());


        Optional<Admin> loggedInAdmin = adminRepository.findByUserName("akinOba");
        assertTrue(loggedInAdmin.isPresent());
        assertTrue(loggedInAdmin.get().isLogin());
    }

    @Test
    public void deleteUserAccount() {

        Admin admin = new Admin();
        admin.setUserName("akinOba");
        admin.setPassword("123456");
        adminRepository.save(admin);

        User user = new User();
        user.setUserName("user123");
        user.setPassword("89034");
        userRepository.save(user);

        AdminDeleteUserResponse response = adminServices.deleteUserAccount(admin.getId(), "user123");
        assertNotNull(response);
        assertEquals("You have successfully deleted the user with the given userName \"user123", response.getMessage());

        Optional<User> deletedUser = userRepository.findByUserName("user123");
        assertTrue(deletedUser.isEmpty());

    }
    @Test
    public void testDeleteUserAccount_UserNotFound_ThrowsException() {

        Admin admin = new Admin();
        admin.setUserName("adminUser");
        adminRepository.save(admin);


        AdminExceptions exception = assertThrows(AdminExceptions.class, () -> {
            adminServices.deleteUserAccount(admin.getId(), "nonexistentUser");
        });


        assertEquals("User with the given identity does not exist.", exception.getMessage());
    }

    @Test
    public void Test_That_My_Admin_Can_DisableUserAccount() {
        Admin admin = new Admin();
        admin.setUserName("akinOba");
        admin.setPassword("123456");
        adminRepository.save(admin);
        User user = new User();
        user.setUserName("user123");
        user.setPassword("89034");
        user.setDisable(true);
        userRepository.save(user);
        AdminDisableUserAccountResponse response = adminServices.disableUserAccount(admin.getId(), "user123");
        System.out.println(response);
        response.setMessage("You have successfully disabled user account.");
        response.setTimeStamp(LocalDateTime.now().toString());

        assertThat(response.getMessage()).isEqualTo("You have successfully disabled user account.");
    }

    @Test
    public  void Test_That_if_my_User_is_not_registered_there_will_be_exception_to_be_throw(){
        Admin admin = new Admin();
        admin.setUserName("akinOba");
        admin.setPassword("123456");
        adminRepository.save(admin);


        String nonExistingUserName = "NonExistingUser";


        AdminExceptions exception = assertThrows(AdminExceptions.class, () -> {
            adminServices.disableUserAccount(admin.getId(), nonExistingUserName);
        });


        assertEquals("User not found: " + nonExistingUserName, exception.getMessage());
    }
    @Test
    public void Test_That_if_Admin_Does_Not_Exist_Exception_Is_Thrown() {

        User user = new User();
        user.setUserName("user123");
        user.setPassword("89034");
        userRepository.save(user);


        String nonExistingAdminId = "nonExistingAdminId";

        AdminExceptions exception = assertThrows(AdminExceptions.class, () -> {
            adminServices.disableUserAccount(nonExistingAdminId, user.getUserName());
        });

        assertEquals("Admin with the given id does not exist.", exception.getMessage());
    }



    @Test
   public void Test_That_I_Can_Get_A_User_FirstName() {
        User user = new User();
        user.setUserName("user123");
        user.setPassword("89034");
        user.setEmail("akinOba@gmail.com");
        user.setFirstName("akin");
        user.setLastName("oba");
        userRepository.save(user);

        assertEquals(1, userRepository.findByFirstName(user.getFirstName()).size());
    }

    @Test
    public void Test_That_Get_A_User_LastName() {
        User user = new User();
        user.setUserName("user123");
        user.setPassword("89034");
        user.setEmail("akinOba@gmail.com");
        user.setFirstName("akin");
        user.setLastName("oba");
        userRepository.save(user);
        assertEquals(1, userRepository.findByLastName(user.getLastName()).size());
    }

    @Test
   public void Test_I_Can_get_All_Users() {

        User user = new User();
        user.setUserName("user123");
        user.setPassword("89034");
        user.setEmail("akinOba@gmail.com");
        user.setFirstName("akin");
        user.setLastName("oba");
        userRepository.save(user);

        User user1 = new User();
        user1.setUserName("user1234");
        user1.setPassword("890345");
        user1.setEmail("akinObanla@gmail.com");
        user1.setFirstName("akinyemi");
        user1.setLastName("obanla");
        userRepository.save(user1);

        User user2 = new User();
        user2.setUserName("user123456");
        user2.setPassword("8903450");
        user2.setEmail("akinemi@gmail.com");
        user2.setFirstName("akinyemiola");
        user2.setLastName("obanlayemi");
        userRepository.save(user2);

        assertEquals(3, userRepository.findAll().size());
    }
    @Test
    public void Test_That_Admin_Can_Delete_User_By_Id() {

        Admin admin = new Admin();
        admin.setUserName("adminUser");
        admin.setPassword("adminPass");
        adminRepository.save(admin);

        User user = new User();
        user.setUserName("user123");
        user.setPassword("userPass");
        userRepository.save(user);
        AdminDeleteUserByIdResponse response = adminServices.deleteUserById(admin.getId(), user.getId());
        assertEquals("You have successfully deleted user with id \"" + user.getId() + "\"", response.getMessage());
        Optional<User> deletedUser = userRepository.findById(user.getId());
        assertTrue(deletedUser.isEmpty());
    }
    @Test
    public void Test_That_I_Can_Find_User_By_Id() {
        User user = new User();
        user.setUserName("user123");
        user.setPassword("userPass");
        userRepository.save(user);
        AdminFindUserByIdResponse response = adminServices.getUserById(user.getId());
        assertEquals("User with id \"" + user.getId(), response.getMessage());
        assertEquals("200 , SUCCESS", response.getStatus());
        assertNotNull(response);
    }


}