package com.rudyk.shopgrid.usersservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogoutRequestDto {

    @NotBlank
    private String refreshToken;

}
