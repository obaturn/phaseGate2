package com.semicolon.africa.data.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class Admin {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String homeAddress;
    private List<User> users = new ArrayList<>();
}
