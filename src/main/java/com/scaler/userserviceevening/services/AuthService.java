package com.scaler.userserviceevening.services;

import com.scaler.userserviceevening.models.Role;
import com.scaler.userserviceevening.models.User;
import com.scaler.userserviceevening.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public User signup(String email, String password) {
        if(userRepository.existsByEmail(email)) {
            return null;
        }

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public String login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty() || !passwordEncoder.matches(password, user.get().getPasswordHash())) {
            return null;
        }

        return jwtService.generateToken(user.get());
    }
}
