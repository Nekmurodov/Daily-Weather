package com.example.dailyWeather.dto;

import com.example.dailyWeather.valid.PasswordValidate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String username;
    @PasswordValidate
    private String password;

}
