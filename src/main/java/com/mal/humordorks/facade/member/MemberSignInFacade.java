package com.mal.humordorks.facade.member;

import com.mal.humordorks.dto.MemberPrintForm;
import com.mal.humordorks.dto.MemberSignInForm;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.service.MemberCommonService;

public class MemberSignInFacade {

    private final MemberCommonService memberCommonService;

    public MemberSignInFacade(MemberCommonService memberCommonService) {
        this.memberCommonService = memberCommonService;
    }

    public MemberPrintForm signInMember(MemberSignInForm memberSignInForm) {
        String email = memberSignInForm.getEmail();
        String password = memberSignInForm.getPassword();
        Member member = memberCommonService.findMember(email);
        memberCommonService.checkPassword(member, password);
        return MemberPrintForm.toDto(member);
    }

}
