package com.example.dailyWeather.service;

import com.example.dailyWeather.dto.RegistrationRequest;
import com.example.dailyWeather.dto.UserDto;
import com.example.dailyWeather.entity.user.User;
import com.example.dailyWeather.exception.AlreadyExistException;
import com.example.dailyWeather.exception.NotFoundException;
import com.example.dailyWeather.mapper.UserMapper;
import com.example.dailyWeather.repository.UserRepository;
import com.example.dailyWeather.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public ResponseData<?> registerUser(RegistrationRequest registrationRequest) {
        if (userRepository.existsByUsernameAndDeletedFalse(registrationRequest.getUsername())) {
            throw new AlreadyExistException("Username already exists!");
        }
        User user = userMapper.toEntity(registrationRequest);
        user.setEnabled(true);
        userRepository.save(user);
        return ResponseData.successResponse(userMapper.toDto(user));
    }

    public ResponseData<?> updateUser(UUID userId, RegistrationRequest registrationRequest) {
        Optional<User> userOptional = userRepository.findByIdAndDeletedFalse(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        if (this.userRepository.existsByUsernameAndDeletedFalse(registrationRequest.getUsername())) {
            throw new AlreadyExistException("Username already exists");
        }
        User user = this.userMapper.updateEntity(userOptional.get(), registrationRequest);
        this.userRepository.save(user);
        return ResponseData.successResponse(this.userMapper.toDto(user));
    }

    public ResponseData<?> deleteUser(UUID userId) {
        Optional<User> userOptional = userRepository.findByIdAndDeletedFalse(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        User user = userOptional.get();
        user.setDeleted(true);
        this.userRepository.save(user);
        return ResponseData.successResponse("success");
    }

    public ResponseData<UserDto> getUser(UUID userId) {
        return userRepository.findByIdAndDeletedFalse(userId)
                .map(userMapper::toDto)
                .map(ResponseData::successResponse)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public ResponseData<?> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = this.userRepository.findAllByDeletedFalse(pageable);
        if (users.getTotalElements() == 0) {
            throw new NotFoundException("Users not found");
        }

        return ResponseData.successResponse(Map.of(
                "data", userMapper.toDto(users.getContent()),
                "totalPages", users.getTotalPages(),
                "total", users.getTotalElements()
        ));
    }

}
