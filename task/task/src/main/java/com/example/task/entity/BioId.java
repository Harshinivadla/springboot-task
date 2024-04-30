package com.example.task.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.Base64;

import static java.lang.Math.random;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class BioId {

    @Id
    private String username;

    private int bpin;

    private String UDID;

//    public static String generateRandomUDID(){
//        SecureRandom secureRandom = new SecureRandom();
//        byte[] randomBytes = new byte[16];
//        secureRandom.nextBytes(randomBytes);
//        return Base64.getEncoder().encodeToString(randomBytes);
//    }
}
