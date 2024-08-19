package com.semicolon.africa.Dto;

import lombok.Data;

@Data
public class AdminDisableAccountRequest {
    private String email;
    private String phoneNumber;
}
