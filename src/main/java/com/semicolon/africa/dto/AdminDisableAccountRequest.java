package com.semicolon.africa.dto;

import lombok.Data;

@Data
public class AdminDisableAccountRequest {
    private String id;
    private String phoneNumber;
}
