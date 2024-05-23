package com.example.task.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class BioId {

    @Id
    private String username;

    private Integer bpin;

    private String UDID;

    public BioId(String username, Integer bpin) {
        this.username = username;
        this.bpin = bpin;
    }

    public BioId(String username, String UDID){
        this.username = username;
        this.UDID = UDID;
    }
}