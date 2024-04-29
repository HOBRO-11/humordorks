package com.mal.humordorks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mal.humordorks.model.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
@Table(name = "ADMIN")
public final class Admin extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADMIN_ID")
    private Long id;

    @Size(min = 2, max = 10)
    @Column(name = "NICKNAME", unique = true)
    private String nickname;

    @Email
    @Column(name = "EMAIL", unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE")
    @Enumerated
    private AdminRole role;

    private Admin() {
    }

    public static Admin createAdmin(@Size(min = 2, max = 10) String nickname, @Email String email, String password) {
        Admin admin = new Admin();
        admin.nickname = nickname;
        admin.email = email;
        admin.password = password;
        admin.role = AdminRole.NONE;
        return admin;
    }

    public void modifyPassword(String password){
        this.password = password;
    }

    public void assignRoleForStaff(){
        this.role = AdminRole.STAFF;
    }

    public void assignRoleForManager(){
        this.role = AdminRole.MANAGER;
    }

    public void terminateRole(){
        this.role = AdminRole.NONE;
    }


}
