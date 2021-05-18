package com.beam.controller.dto;

import com.beam.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDTO {

    private String token;
    private Role role;
    private int id;
    private String username;

}
