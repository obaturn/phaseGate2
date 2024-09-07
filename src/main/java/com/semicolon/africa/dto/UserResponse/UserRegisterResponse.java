package com.semicolon.africa.dto.UserResponse;

import lombok.Data;

@Data
public class UserRegisterResponse {
    private String message;
    private String statusMessage;
    private String firstName;
    private String lastName;
}
