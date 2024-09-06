package com.semicolon.africa.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserStoreResponse {
    private String status;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();
    private LocalDateTime created = LocalDateTime.now();
}
