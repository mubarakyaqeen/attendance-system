package com.attendance.attendance_system.config;

import com.attendance.attendance_system.model.Role;
import com.attendance.attendance_system.model.User;
import com.attendance.attendance_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if(userRepository.findByEmail("admin@gmail.com").isEmpty()){

            User admin = new User();

            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setFullName("System Admin");
            user.setPasswordHash(passwordEncoder.encode("123456")); // if applicable
            user.setRole(Role.ADMIN);
            userRepository.save(user);

            System.out.println("Default admin created");

        }

    }
}