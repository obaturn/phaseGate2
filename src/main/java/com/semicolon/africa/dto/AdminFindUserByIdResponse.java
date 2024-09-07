package com.semicolon.africa.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AdminFindUserByIdResponse {
    private String Message;
    private String Status;
    private LocalDateTime timeStamp;
}
