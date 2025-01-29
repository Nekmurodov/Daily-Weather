package com.example.dailyWeather.controller;

import com.example.dailyWeather.dto.RegistrationRequest;
import com.example.dailyWeather.response.ResponseData;
import com.example.dailyWeather.service.UserService;
import com.example.dailyWeather.util.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user/")
public class UserController {

    private final UserService userService;
    private final UserSession userSession;

    @PutMapping("update/{userId}")
    public ResponseData<?> update(@PathVariable UUID userId, RegistrationRequest registrationRequest) {
        return this.userService.updateUser(userId, registrationRequest);
    }

    @DeleteMapping("delete/{userId}")
    public ResponseData<?> delete(@PathVariable UUID userId) {
        return this.userService.deleteUser(userId);
    }

    @GetMapping("/get/{userId}")
    public ResponseData<?> get(@PathVariable UUID userId) {
        return this.userService.getUser(userId);
    }

    @GetMapping("/get-one")
    public ResponseData<?> get() {
        return this.userService.getUser(userSession.getUser().getId());
    }

    @GetMapping("/get-all")
    public ResponseData<?> getAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        return this.userService.getAllUsers(page,size);
    }

}
