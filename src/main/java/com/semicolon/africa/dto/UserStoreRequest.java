package com.semicolon.africa.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserStoreRequest {
    private String Title;
    private String Body;
    private String Email;

}
