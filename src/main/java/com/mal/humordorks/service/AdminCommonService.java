package com.mal.humordorks.service;

import org.springframework.data.domain.Page;

import com.mal.humordorks.dto.AdminSignUpForm;
import com.mal.humordorks.model.Admin;
import com.mal.humordorks.model.AdminRole;

public interface AdminCommonService {

    Admin createAdmin(AdminSignUpForm adminSignUpForm);

    Admin findAdmin(long id);

    Page<Admin> findAllAdmin(int page, int size);

    Page<Admin> findAllByRole(AdminRole role, int page, int size);

    void assignRoleForStaff(Admin manager, Admin admin);

    void assignRoleForManager(Admin manager, Admin admin);

    void terminateRole(Admin manager, Admin admin);

    void deleteAdmin(Admin admin);

}