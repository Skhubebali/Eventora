package com.eventora.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateDto {


    private String username;


    private String password;
}
