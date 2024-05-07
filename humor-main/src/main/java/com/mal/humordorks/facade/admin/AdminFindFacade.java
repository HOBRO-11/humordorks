package com.mal.humordorks.facade.admin;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.mal.humordorks.dto.AdminPrintDto;
import com.mal.humordorks.dto.PagedResponse;
import com.mal.humordorks.model.Admin;
import com.mal.humordorks.model.AdminRole;
import com.mal.humordorks.service.AdminCommonService;

public class AdminFindFacade {
    private final AdminCommonService adminCommonService;

    public AdminFindFacade(AdminCommonService adminCommonService) {
        this.adminCommonService = adminCommonService;
    }

    public Admin findAdmin(long id) {
        return adminCommonService.findAdmin(id);
    }

    public PagedResponse<AdminPrintDto> findAllAdmin(int page, int size) {
        Page<Admin> pages = adminCommonService.findAllAdmin(page, size);
        return getPageResponseFromComment(pages);
    }

    public PagedResponse<AdminPrintDto> findAllByRole(AdminRole role, int page, int size){
        Page<Admin> pages = adminCommonService.findAllByRole(role, page, size);
        return getPageResponseFromComment(pages);
    }

    private PagedResponse<AdminPrintDto> getPageResponseFromComment(Page<Admin> pages) {
        List<AdminPrintDto> content = null;
        if (pages.getNumberOfElements() == 0) {
            content = Collections.emptyList();
        } else {
            content = pages.stream()
                    .map(page -> AdminPrintDto.toDto(page.getNickname(), page.getEmail(), page.getRole()))
                    .collect(Collectors.toList());
        }
        return new PagedResponse<>(content, pages.getNumber(), pages.getSize(), pages.getTotalElements(),
                pages.getTotalPages(), pages.isLast());
    }

}
