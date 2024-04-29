package com.mal.humordorks.facade.admin;

import com.mal.humordorks.dto.AdminPrintDto;
import com.mal.humordorks.dto.AdminSignInDto;
import com.mal.humordorks.exception.UnAuthAdminException;
import com.mal.humordorks.model.Admin;
import com.mal.humordorks.service.AdminCommonService;

public class AdminSignInFacade {
    private final AdminCommonService adminCommonService;

    public AdminSignInFacade(AdminCommonService adminCommonService) {
        this.adminCommonService = adminCommonService;
    }

    public AdminPrintDto signIn(AdminSignInDto dto){
        String email = dto.getEmail();
        String password = dto.getPassword();
        Admin admin = adminCommonService.findByEmail(email);
        if(admin.getPassword() != password){
            throw new UnAuthAdminException("this admin not auth");
        }
        return AdminPrintDto.toDto(password, email, admin.getRole());
    }
    
}
