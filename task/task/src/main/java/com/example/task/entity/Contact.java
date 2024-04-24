package com.example.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task")

public class Contact {

    @Id
    private int id;

    private String name;

    private String mobNo;

}
