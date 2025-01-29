package com.example.dailyWeather.valid;

import com.example.dailyWeather.util.RestConstant;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidation implements ConstraintValidator<PasswordValidate, String> {

    public Pattern pattern;
    public Matcher matcher;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isValidPassword(value);
    }
    private boolean isValidPassword(String password){
        pattern = Pattern.compile(RestConstant.PASSWORD_REGEX);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
