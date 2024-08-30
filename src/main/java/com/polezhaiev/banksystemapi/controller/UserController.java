package com.polezhaiev.banksystemapi.controller;

import com.polezhaiev.banksystemapi.dto.user.UserDetailedInfoRequestDto;
import com.polezhaiev.banksystemapi.dto.user.UserDetailedInfoResponseDto;
import com.polezhaiev.banksystemapi.dto.user.UserRegistrationRequestDto;
import com.polezhaiev.banksystemapi.dto.user.UserResponseDto;
import com.polezhaiev.banksystemapi.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Bank system api", description = "Endpoints for bank system api")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a user", description = "Register a user")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto) {
        return userService.register(requestDto);
    }

    @GetMapping("/")
    @Operation(summary = "Get user's detailed info", description = "Get user's detailed info")
    public UserDetailedInfoResponseDto getDetailedInfo(@RequestBody @Valid UserDetailedInfoRequestDto requestDto) {
        return userService.getDetailedInfo(requestDto);
    }

    @GetMapping()
    @Operation(summary = "Find all users by the phone number", description = "Find all users by the phone number")
    public List<UserResponseDto> getDetailedInfo() {
        return userService.findAll();
    }
}
