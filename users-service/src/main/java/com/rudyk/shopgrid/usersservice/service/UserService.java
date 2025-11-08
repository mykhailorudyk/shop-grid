package com.rudyk.shopgrid.usersservice.service;

import com.rudyk.shopgrid.usersservice.dto.RegisterUserRequestDto;
import com.rudyk.shopgrid.usersservice.dto.UserResponseDto;

import java.util.UUID;

public interface UserService {

    UserResponseDto getUserById(UUID id);

    UserResponseDto registerUser(RegisterUserRequestDto registerRequestDto);
}
