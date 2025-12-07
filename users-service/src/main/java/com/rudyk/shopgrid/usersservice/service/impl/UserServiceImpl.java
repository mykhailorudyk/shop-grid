package com.rudyk.shopgrid.usersservice.service.impl;

import com.rudyk.shopgrid.common.exception.ResourceNotFoundException;
import com.rudyk.shopgrid.usersservice.dto.RegisterUserRequestDto;
import com.rudyk.shopgrid.usersservice.dto.UserResponseDto;
import com.rudyk.shopgrid.usersservice.entity.User;
import com.rudyk.shopgrid.usersservice.mapper.UserMappper;
import com.rudyk.shopgrid.usersservice.repository.UserRepository;
import com.rudyk.shopgrid.usersservice.service.KeycloakService;
import com.rudyk.shopgrid.usersservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;

    @Override
    public UserResponseDto getUserById(String id) {
        return userRepository.findById(id).map(UserMappper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public UserResponseDto registerUser(RegisterUserRequestDto registerRequestDto) {
        String createdUserId = keycloakService.createKeycloakUser(registerRequestDto);
        User localUser = new User();
        localUser.setId(createdUserId);
        UserMappper.mapToEntity(localUser, registerRequestDto);
        userRepository.save(localUser);
        return UserMappper.mapToDto(localUser);
    }

}
