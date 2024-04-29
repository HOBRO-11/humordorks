package com.mal.humordorks.dto;

import com.mal.humordorks.model.AdminRole;

import lombok.Data;

@Data
public class AdminPrintDto {

    private String nickname;
    private String email;
    private AdminRole role;

    public static AdminPrintDto toDto(String nickname, String email, AdminRole role) {
        AdminPrintDto adminPrintDto = new AdminPrintDto();
        adminPrintDto.nickname = nickname;
        adminPrintDto.email = email;
        adminPrintDto.role = role;
        return adminPrintDto;
    }

}
