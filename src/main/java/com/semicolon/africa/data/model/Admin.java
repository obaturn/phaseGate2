package com.semicolon.africa.data.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@Document
public class Admin {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String homeAddress;
    @DBRef
    private List<User> users;
    private boolean accountDisable;
    private String phoneNumber;
    private String gender;
}

