package com.example.dailyWeather.dto;

import com.example.dailyWeather.valid.PasswordValidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private String name;
    private String surname;
    private String username;
    @PasswordValidate
    private String password;

}
