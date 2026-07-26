package com.bank.config;

import com.bank.UserEntity.User;
import com.bank.enums.Role;
import com.bank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
            User admin = User.builder()
                    .name("Admin")
                    .email("admin@gmail.com")
                    .password("admin123")
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(admin);
            System.out.println("Default admin user created");
        }
    }
}