package com.example.dailyWeather.mapper;

import com.example.dailyWeather.dto.RegistrationRequest;
import com.example.dailyWeather.dto.UserDto;
import com.example.dailyWeather.entity.user.User;
import com.example.dailyWeather.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toEntity(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setName(registrationRequest.getName());
        user.setSurname(registrationRequest.getSurname());
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setRole(Role.USER);
        return user;
    }

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());
        return userDto;
    }

    public User updateEntity(User user, RegistrationRequest registrationRequest) {
        if (registrationRequest.getName() != null) {
            user.setName(registrationRequest.getName());
        }
        if (registrationRequest.getSurname() != null) {
            user.setSurname(registrationRequest.getSurname());
        }
        if (registrationRequest.getUsername() != null) {
            user.setUsername(registrationRequest.getUsername());
        }
        if (registrationRequest.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        }
        return user;
    }


    public List<UserDto> toDto(List<User> users) {
        List<UserDto> userDto = new ArrayList<>();
        for (User user : users) {
            userDto.add(toDto(user));
        }
        return userDto;
    }
}
