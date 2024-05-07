package com.mal.humordorks.facade.admin;

import com.mal.humordorks.dto.AdminPrintDto;
import com.mal.humordorks.dto.AdminSignUpForm;
import com.mal.humordorks.model.Admin;
import com.mal.humordorks.model.AdminRole;
import com.mal.humordorks.service.AdminCommonService;

public class AdminRegisterFacade {
    
    private final AdminCommonService adminCommonService;

    public AdminRegisterFacade(AdminCommonService adminCommonService) {
        this.adminCommonService = adminCommonService;
    }

    public AdminPrintDto createAdmin(AdminSignUpForm asf){
        Admin admin = adminCommonService.createAdmin(asf);
        String nickname = admin.getNickname();
        String email = admin.getEmail();
        AdminRole role = admin.getRole();
        return AdminPrintDto.toDto(nickname, email, role);
    }

    
}
