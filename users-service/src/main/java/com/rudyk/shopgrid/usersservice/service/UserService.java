package com.rudyk.shopgrid.usersservice.service;

import com.rudyk.shopgrid.usersservice.dto.RegisterUserRequestDto;
import com.rudyk.shopgrid.usersservice.dto.UserResponseDto;

public interface UserService {

    UserResponseDto getUserById(String id);

    UserResponseDto registerUser(RegisterUserRequestDto registerRequestDto);
}
