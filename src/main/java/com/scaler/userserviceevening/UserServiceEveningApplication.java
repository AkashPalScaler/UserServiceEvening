package com.scaler.userserviceevening;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UserServiceEveningApplication {


    // By default - function name is the name of the bean
    @Bean
    public PasswordEncoder passwordEncoder(){
        // Custom logic of creating my object and handing it over to spring context
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(UserServiceEveningApplication.class, args);
    }

}
