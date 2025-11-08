package com.rudyk.shopgrid.usersservice.dto;

import lombok.Data;

@Data
public class RegisterUserRequestDto {

    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

}
