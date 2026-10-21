package com.eventora.dto;

import lombok.Data;

@Data
public class LoginResponseDto {

    private String jwtToken;

    private UserDto userDto;
}
