package com.example.dailyWeather.util;

import com.example.dailyWeather.enums.Role;
import com.example.dailyWeather.factory.UserFactorySingleton;
import com.example.dailyWeather.repository.UserRepository;
import com.example.dailyWeather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WeatherService weatherService;

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
                    Role.ADMIN));

            List<String> countries = List.of("Tashkent", "Moscow", "New York", "Tokyo", "London");
            countries.forEach(weatherService::getWeather);

        }
    }
}
