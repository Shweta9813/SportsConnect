package com.sportsconnect.backend.controller;

import com.sportsconnect.backend.dto.UserLoginRequest;
import com.sportsconnect.backend.dto.UserRegistrationRequest;
import com.sportsconnect.backend.dto.UserResponse;
import com.sportsconnect.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid@RequestBody UserRegistrationRequest request) {

        UserResponse response = userService.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
             @RequestBody UserLoginRequest request) {

        String response = userService.loginUser(request);

        return ResponseEntity.ok(response);
    }
}