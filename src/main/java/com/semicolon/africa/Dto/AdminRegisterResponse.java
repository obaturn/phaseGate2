package com.semicolon.africa.Dto;

import lombok.Data;

@Data
public class AdminRegisterResponse {
    private String statusMessage;
    private String message;
    private String firstName;
    private String lastName;

}
