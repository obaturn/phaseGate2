package com.semicolon.africa.Dto;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gmail;
    private String phoneNumber;
    private String address;
    private String userName;
}
