package com.semicolon.africa.data.model;

import com.semicolon.africa.dto.UserRequest.UserStoreRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String userName;
    private String gender;
    private boolean login;
    private boolean isDisable;
    private String confirmPassword;
    private UserStoreRequest request;
    private String  tittle;
    private String body;

}
