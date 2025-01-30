package com.example.dailyWeather.factory;

import com.example.dailyWeather.entity.user.User;
import com.example.dailyWeather.enums.Role;


public class UserFactorySingleton {


    private static UserFactorySingleton instance;

    private UserFactorySingleton() {
    }

    public static synchronized UserFactorySingleton getInstance() {
        if (instance == null) {
            instance = new UserFactorySingleton();
        }
        return instance;
    }

    public User createUser(String name, String surname, String username, String password, Role role) {
        return User.builder()
                .name(name)
                .surname(surname)
                .username(username)
                .password(password)
                .role(role)
                .enabled(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .build();
    }

}
