package com.semicolon.africa.Dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String email;
    private String password;
    private String userName;
}
