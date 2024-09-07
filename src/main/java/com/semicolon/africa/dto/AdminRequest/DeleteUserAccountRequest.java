package com.semicolon.africa.dto.AdminRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteUserAccountRequest {
    private String adminEmail;
    private String username;
}
