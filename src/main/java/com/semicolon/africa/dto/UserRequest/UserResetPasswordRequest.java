package com.semicolon.africa.dto.UserRequest;

import lombok.Data;

@Data
public class UserResetPasswordRequest {
    private String firstName;
    private String password;
    private String email;
}
