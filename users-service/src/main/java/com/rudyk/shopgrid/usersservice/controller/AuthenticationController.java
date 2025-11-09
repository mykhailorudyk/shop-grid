package com.rudyk.shopgrid.usersservice.controller;

import com.rudyk.shopgrid.usersservice.dto.LoginRequestDto;
import com.rudyk.shopgrid.usersservice.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDto loginRequestDto) {
        Object accessTokens = loginService.login(loginRequestDto);
        return ResponseEntity.ok(accessTokens);
    }

}
