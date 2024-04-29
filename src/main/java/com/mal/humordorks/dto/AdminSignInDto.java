package com.mal.humordorks.dto;

import lombok.Data;

@Data
public class AdminSignInDto {

    private String email;
    private String password;

    public AdminSignInDto toDto(String email, String password) {
        AdminSignInDto dto = new AdminSignInDto();
        dto.email = email;
        dto.password = password;
        return dto;
    }
}
