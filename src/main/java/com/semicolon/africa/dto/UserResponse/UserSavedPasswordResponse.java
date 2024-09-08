package com.semicolon.africa.dto.UserResponse;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserSavedPasswordResponse {
    private String message;
    private LocalDateTime dateTime;

}
