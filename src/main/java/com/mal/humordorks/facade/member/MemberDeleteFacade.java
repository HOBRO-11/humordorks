package com.mal.humordorks.facade.member;

import com.mal.humordorks.model.Member;
import com.mal.humordorks.service.MemberCommonService;

public class MemberDeleteFacade {
    
    private final MemberCommonService memberCommonService;

    public MemberDeleteFacade(MemberCommonService memberCommonService) {
        this.memberCommonService = memberCommonService;
    }

    public void deleteMember(long id){
        Member member = memberCommonService.findMember(id);
        memberCommonService.deleteMember(member);
    }
}
