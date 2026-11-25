package com.scaler.userserviceevening.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {
    private String email;
    private String password;
}

// Use the above dto for both sign up and login
// Ideally sign up will actually have more properties like name, address, age, etc
