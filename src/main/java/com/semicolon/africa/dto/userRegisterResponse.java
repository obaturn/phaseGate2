package com.semicolon.africa.dto;

import lombok.Data;

@Data
public class userRegisterResponse {
    private String message;
    private String statusMessage;
    private String firstName;
    private String lastName;
    private String userName;

}
