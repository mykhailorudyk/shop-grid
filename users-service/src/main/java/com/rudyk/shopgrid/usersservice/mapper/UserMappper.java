package com.rudyk.shopgrid.usersservice.mapper;

import com.rudyk.shopgrid.usersservice.dto.RegisterUserRequestDto;
import com.rudyk.shopgrid.usersservice.dto.UserResponseDto;
import com.rudyk.shopgrid.usersservice.entity.User;

public class UserMappper {

    public static UserResponseDto mapToDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

}
