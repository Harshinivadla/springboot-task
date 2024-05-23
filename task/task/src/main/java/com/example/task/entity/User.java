package com.example.task.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_details")

public class User {

    @Id
    private String username;

    private String password;

    private String newPassword;

    public User(String username,String password) {
        this.username = username;
        this.password = password;
    }

    @ElementCollection
    private List<String> passwordHistory = new ArrayList<>();

}