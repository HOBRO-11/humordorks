package com.mal.humordorks.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.mal.humordorks.dto.AdminSignUpForm;
import com.mal.humordorks.exception.ResourceExistException;
import com.mal.humordorks.exception.ResourceNotFound;
import com.mal.humordorks.exception.UnAuthAdminException;
import com.mal.humordorks.model.Admin;
import com.mal.humordorks.model.AdminRole;
import com.mal.humordorks.repository.AdminRepository;
import com.mal.humordorks.service.AdminCommonService;

public class AdminCommonServiceImpl implements AdminCommonService {

    @Override
    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFound("this admin not found"));
    }

    private final AdminRepository adminRepository;

    public AdminCommonServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin createAdmin(AdminSignUpForm adminSignUpForm) {
        String nickname = adminSignUpForm.getNickname();
        String email = adminSignUpForm.getEmail();
        String password = adminSignUpForm.getPassword();
        return Admin.createAdmin(nickname, email, password);
    }

    @Override
    public Admin findAdmin(long id) {
        return adminRepository.findById(id).orElseThrow(() -> new ResourceNotFound("this admin not found"));
    }

    @Override
    public Page<Admin> findAllAdmin(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return adminRepository.findAll(pageable);
    }

    @Override
    public Page<Admin> findAllByRole(AdminRole role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return adminRepository.findAllByAdminRole(role, pageable);
    }

    @Override
    public void modifyPassword(Admin admin, String password) {
        if (admin.getPassword() == password) {
            throw new ResourceExistException("Previously used passwords cannot be used.");
        }
        admin.modifyPassword(password);
    }

    @Override
    public void assignRoleForStaff(Admin manager, Admin admin) {
        checkAuthor(manager);
        admin.assignRoleForStaff();
    }

    @Override
    public void assignRoleForManager(Admin manager, Admin admin) {
        checkAuthor(manager);
        admin.assignRoleForManager();
    }

    @Override
    public void terminateRole(Admin manager, Admin admin) {
        checkAuthor(manager);
        admin.terminateRole();
    }

    @Override
    public void deleteAdmin(Admin admin) {
        adminRepository.delete(admin);
    }

    private void checkAuthor(Admin manager) {
        if (manager.getRole() != AdminRole.MANAGER) {
            throw new UnAuthAdminException("this admin not manager");
        }
    }

}
