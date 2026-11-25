package com.scaler.userserviceevening.dtos;

import com.scaler.userserviceevening.models.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private Role role;
}
// sign up - (input)AUthReqDto ->  (output)UserDto
// login - (input)AuthReqDto -> (output) LoginResponseDto