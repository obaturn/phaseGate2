package com.semicolon.africa.dto;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userName;
    private String phoneNumber;
}
