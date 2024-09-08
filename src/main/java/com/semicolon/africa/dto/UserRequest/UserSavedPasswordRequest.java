package com.semicolon.africa.dto.UserRequest;

import lombok.Data;

@Data
public class UserSavedPasswordRequest {
    private String firstName;
    private String lastName;
    private String password;
}
