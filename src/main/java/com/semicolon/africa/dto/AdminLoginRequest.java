package com.semicolon.africa.dto;

import lombok.Data;

@Data
public class AdminLoginRequest {
    private String userName;
    private String password;
}
