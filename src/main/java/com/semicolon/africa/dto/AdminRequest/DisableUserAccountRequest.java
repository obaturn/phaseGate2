package com.semicolon.africa.dto.AdminRequest;

import lombok.Data;

@Data
public class DisableUserAccountRequest {
    private String email;
    private String username;
}
