package com.semicolon.africa.dto.UserRequest;

import lombok.Data;

@Data
public class UserRetrievePasswordRequest {
    private String email;
    private String userName;

}
