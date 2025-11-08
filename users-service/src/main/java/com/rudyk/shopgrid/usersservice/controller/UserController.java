package com.rudyk.shopgrid.usersservice.controller;

import com.rudyk.shopgrid.usersservice.dto.RegisterUserRequestDto;
import com.rudyk.shopgrid.usersservice.dto.UserResponseDto;
import com.rudyk.shopgrid.usersservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable("id") UUID id) {
        UserResponseDto responseDto = userService.getUserById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody RegisterUserRequestDto registerRequestDto) {
        UserResponseDto responseDto = userService.registerUser(registerRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

}
