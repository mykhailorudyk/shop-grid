package com.rudyk.shopgrid.usersservice.service.impl;

import com.rudyk.shopgrid.common.exception.ResourceDuplicationException;
import com.rudyk.shopgrid.common.exception.ResourceNotFoundException;
import com.rudyk.shopgrid.usersservice.dto.RegisterUserRequestDto;
import com.rudyk.shopgrid.usersservice.dto.UserResponseDto;
import com.rudyk.shopgrid.usersservice.entity.User;
import com.rudyk.shopgrid.usersservice.mapper.UserMappper;
import com.rudyk.shopgrid.usersservice.repository.UserRepository;
import com.rudyk.shopgrid.usersservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto getUserById(UUID id) {
        return userRepository.findById(id).map(UserMappper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public UserResponseDto registerUser(RegisterUserRequestDto registerRequestDto) {
        User user = new User();
        setUsername(registerRequestDto, user);
        setPassword(registerRequestDto, user);
        setUserDetails(registerRequestDto, user);
        userRepository.save(user);
        return UserMappper.mapToDto(user);
    }

    private void setUsername(RegisterUserRequestDto registerRequestDto, User user) {
        if (!isUserExist(registerRequestDto.getUsername())) {
            user.setUsername(registerRequestDto.getUsername());
        } else {
            throw new ResourceDuplicationException("User", "username", registerRequestDto.getUsername());
        }
    }

    private void setPassword(RegisterUserRequestDto registerRequestDto, User user) {
        String encryptedPassword = encryptPassword(registerRequestDto.getPassword());
        user.setPassword(encryptedPassword);
    }

    private static void setUserDetails(RegisterUserRequestDto registerRequestDto, User user) {
        user.setFirstName(registerRequestDto.getFirstName());
        user.setLastName(registerRequestDto.getLastName());
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean isUserExist(String username) {
        return !userRepository.findByUsername(username).isEmpty();
    }
}
