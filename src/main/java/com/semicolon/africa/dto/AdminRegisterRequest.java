package com.semicolon.africa.dto;

import lombok.Data;

@Data
public class AdminRegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String password;
    private String confirmPassword;
    private String userName;
    private String homeAddress;
    private String confirmedEmail;
}
