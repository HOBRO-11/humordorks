package com.mal.humordorks.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mal.humordorks.model.Admin;
import com.mal.humordorks.model.AdminRole;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Page<Admin> findAllByAdminRole(AdminRole role, Pageable pageable);

    Optional<Admin> findByEmail(String email);

}
