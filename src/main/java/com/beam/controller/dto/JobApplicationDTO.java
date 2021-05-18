package com.beam.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobApplicationDTO {

    private int id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private String job;
    private String skills;
    private String languages;
    private UserDTO userDTO;

}
