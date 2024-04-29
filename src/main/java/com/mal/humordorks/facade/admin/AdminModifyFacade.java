package com.mal.humordorks.facade.admin;

import com.mal.humordorks.model.Admin;
import com.mal.humordorks.service.AdminCommonService;

public class AdminModifyFacade {
    
    private final AdminCommonService adminCommonService;

    public AdminModifyFacade(AdminCommonService adminCommonService) {
        this.adminCommonService = adminCommonService;
    }

    public void assignRoleForStaff(long managerId, long adminId){
        Admin manager = adminCommonService.findAdmin(managerId);
        Admin admin = adminCommonService.findAdmin(adminId);
        adminCommonService.assignRoleForStaff(manager, admin);
    }

    public void assignRoleForManager(long managerId, long adminId){
        Admin manager = adminCommonService.findAdmin(managerId);
        Admin admin = adminCommonService.findAdmin(adminId);
        adminCommonService.assignRoleForManager(manager, admin);
    }

    public void terminateRole(long managerId, long adminId){
        Admin manager = adminCommonService.findAdmin(managerId);
        Admin admin = adminCommonService.findAdmin(adminId);
        adminCommonService.terminateRole(manager, admin);
    }

    public void changePassword(long id, String password){
        Admin admin = adminCommonService.findAdmin(id);
        adminCommonService.modifyPassword(admin, password);
    }
}
