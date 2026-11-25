package com.scaler.userserviceevening.controller;

import com.scaler.userserviceevening.dtos.AuthRequestDto;
import com.scaler.userserviceevening.dtos.LoginResponseDto;
import com.scaler.userserviceevening.dtos.UserDto;
import com.scaler.userserviceevening.models.Role;
import com.scaler.userserviceevening.models.User;
import com.scaler.userserviceevening.services.AuthService;
import com.scaler.userserviceevening.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody AuthRequestDto authRequestDto) {
        try{
            User user = authService.signup(authRequestDto.getEmail(), authRequestDto.getPassword());
            if(user == null){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setEmail(user.getEmail());
            userDto.setRole(user.getRole());
            return new ResponseEntity<>(userDto, HttpStatus.CREATED);
        }catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        String token = authService.login(authRequestDto.getEmail(), authRequestDto.getPassword());
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setToken(token);
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }

    @GetMapping("/validate")
    public ResponseEntity<UserDto> validateToken(@RequestHeader("Authorization") String authHeader) {
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try{
            String token = authHeader.substring("Bearer ".length());
            Claims claims = jwtService.validateAndParse(token);
            UserDto userDto = new UserDto();
            userDto.setEmail(claims.getSubject());
            userDto.setRole(Role.valueOf(claims.get("role", String.class)));
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }catch (JwtException ex){
            return  new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
