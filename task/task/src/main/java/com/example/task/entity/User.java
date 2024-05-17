package com.example.task.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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

    @ElementCollection
    private List<String> passwordHistory;

}