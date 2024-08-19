package com.semicolon.africa.Dto;

import lombok.Data;

@Data
public class UserCreatePasswordRequest {
    private String firstName;
    private String lastName;
    private String password;
}
