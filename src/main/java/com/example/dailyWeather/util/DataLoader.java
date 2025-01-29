package com.example.dailyWeather.util;

import com.example.dailyWeather.enums.Permissions;
import com.example.dailyWeather.enums.Role;
import com.example.dailyWeather.factory.UserFactorySingleton;
import com.example.dailyWeather.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String initMode;

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {
            UserFactorySingleton instance = UserFactorySingleton.getInstance();

            userRepository.save(instance.createUser(
                    "admin",
                    "Admin",
                    "admin",
                    passwordEncoder.encode("123"),
                    Role.ADMIN,
                    Arrays.stream(Permissions.values()).toList()));
        }
    }
}
