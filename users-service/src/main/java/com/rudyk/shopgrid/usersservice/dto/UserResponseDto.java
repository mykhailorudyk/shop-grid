package com.rudyk.shopgrid.usersservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserResponseDto {

    private String id;
    private String username;
    private String firstName;
    private String lastName;

}
