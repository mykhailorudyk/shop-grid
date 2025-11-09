package com.rudyk.shopgrid.usersservice.mapper;

import com.rudyk.shopgrid.usersservice.dto.RegisterUserRequestDto;
import com.rudyk.shopgrid.usersservice.dto.UserResponseDto;
import com.rudyk.shopgrid.usersservice.entity.User;
import org.keycloak.representations.idm.UserRepresentation;

public class UserMappper {

    public static UserResponseDto mapToDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public static void mapToKeycloakUser(UserRepresentation keycloakUser, RegisterUserRequestDto requestDto) {
        keycloakUser.setUsername(requestDto.getUsername());
        keycloakUser.setEmail(requestDto.getEmail());
        keycloakUser.setFirstName(requestDto.getFirstName());
        keycloakUser.setLastName(requestDto.getLastName());
    }

    public static void mapToEntity(User user, RegisterUserRequestDto requestDto) {
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
    }

}
