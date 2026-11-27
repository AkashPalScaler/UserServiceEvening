package com.scaler.userserviceevening.controller;

import com.scaler.userserviceevening.dtos.UserDto;
import com.scaler.userserviceevening.models.User;
import com.scaler.userserviceevening.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserContoller {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setRole(user.getRole());
        return ResponseEntity.ok(userDto);
    }

}
