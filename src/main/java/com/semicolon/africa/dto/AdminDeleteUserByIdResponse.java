package com.semicolon.africa.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AdminDeleteUserByIdResponse {
    private String message;
    private String status;
    private LocalDateTime timestamp;
}
