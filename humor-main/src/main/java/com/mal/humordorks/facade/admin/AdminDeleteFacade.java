package com.mal.humordorks.facade.admin;

import com.mal.humordorks.model.Admin;
import com.mal.humordorks.service.AdminCommonService;

public class AdminDeleteFacade {
    
    private final AdminCommonService adminCommonService;

    public AdminDeleteFacade(AdminCommonService adminCommonService) {
        this.adminCommonService = adminCommonService;
    }

    public void deleteAdmin(long id){
        Admin admin = adminCommonService.findAdmin(id);
        adminCommonService.deleteAdmin(admin);
    }

}
