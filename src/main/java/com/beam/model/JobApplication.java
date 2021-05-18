package com.beam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fullName;


    @Column(unique = true)
    private String phoneNumber;


    @Column(unique = true)
    private String email;


    private String address;


    private String job;


    private String skills;


    private String languages;

    @OneToOne()
    private Login login;

}
