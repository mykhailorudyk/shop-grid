package com.rudyk.shopgrid.usersservice.service;

import com.rudyk.shopgrid.usersservice.dto.RegisterUserRequestDto;

public interface KeycloakService {

    String createKeycloakUser(RegisterUserRequestDto registerUserRequestDto);

}
