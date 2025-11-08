package com.rudyk.shopgrid.usersservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RegisterUserRequestDto {

    private UUID id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

}
