package com.example.dailyWeather.service;

import com.example.dailyWeather.exception.NotFoundException;
import com.example.dailyWeather.repository.UserRepository;
import com.example.dailyWeather.util.MessageKey;
import com.example.dailyWeather.util.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.isBlank() || username.isEmpty()){
            throw new UsernameNotFoundException(MessageService.getMessage(MessageKey.NULL_USERNAME_FROM_TOKEN));
        }
        return userRepository.findByUsernameAndDeletedFalse(username).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.USERNAME_NOT_FOUND)));

    }
}
