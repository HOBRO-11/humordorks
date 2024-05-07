package com.mal.humordorks.facade.member;

import com.mal.humordorks.dto.MemberPrintForm;
import com.mal.humordorks.dto.MemberSignUpForm;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.service.MemberCommonService;

public class MemberSignUpFacade {
    
    private final MemberCommonService memberCommonService;

    public MemberSignUpFacade(MemberCommonService memberCommonService) {
        this.memberCommonService = memberCommonService;
    }

    public void checkEmail(String email){
        memberCommonService.isUseableEmail(email);
    }

    public void checkNickname(String nickname){
        memberCommonService.isUseableNickname(nickname);
    }

    public MemberPrintForm signUpMember(MemberSignUpForm memberSignUpForm) {
        String email = memberSignUpForm.getEmail();
        String nickname = memberSignUpForm.getNickname();
        memberCommonService.isUseableEmail(email);
        memberCommonService.isUseableNickname(nickname);
        Member member = memberCommonService.createMember(memberSignUpForm);
        return MemberPrintForm.toDto(member);
    }

}
