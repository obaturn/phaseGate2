package com.semicolon.africa.dto;

import lombok.Data;

@Data
public class UserResetPasswordRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
