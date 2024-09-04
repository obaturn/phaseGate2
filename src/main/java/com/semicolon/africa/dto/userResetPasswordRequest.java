package com.semicolon.africa.dto;

import lombok.Data;

@Data
public class userResetPasswordRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
