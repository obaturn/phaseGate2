package com.semicolon.africa.dto.UserResponse;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserRetrievePasswordResponse {
    private String message;
    private String status;
    private LocalDateTime timestamp;
    private String password;
}
