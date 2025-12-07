package com.rudyk.shopgrid.usersservice.service;

import com.rudyk.shopgrid.usersservice.dto.LoginRequestDto;
import com.rudyk.shopgrid.usersservice.dto.LogoutRequestDto;
import com.rudyk.shopgrid.usersservice.dto.RefreshTokenRequestDto;

public interface LoginService {

    Object login(LoginRequestDto loginRequestDto);

    Object refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);

    void logout(LogoutRequestDto logoutRequestDto);
}
