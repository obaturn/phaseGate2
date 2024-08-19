package com.semicolon.africa.Dto;

import lombok.Data;

@Data
public class AdminRegisterRequest {
    private String firstName;
    private String lastName;
    private String homeAddress;
    private String phoneNumber;
    private String email;
    private String gender;
}
