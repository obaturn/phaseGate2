package com.semicolon.africa.dto;

import lombok.Data;

@Data
public class ApiResponse {
    private boolean success;
    private Object data;
}
