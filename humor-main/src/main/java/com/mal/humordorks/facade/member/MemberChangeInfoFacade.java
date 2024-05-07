package com.mal.humordorks.facade.member;

import org.apache.coyote.BadRequestException;

import com.mal.humordorks.dto.MemberPrintForm;
import com.mal.humordorks.exception.UnAuthMemberException;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.service.MemberCommonService;

public class MemberChangeInfoFacade {

    private final MemberCommonService memberCommonService;

    public MemberChangeInfoFacade(MemberCommonService memberCommonService) {
        this.memberCommonService = memberCommonService;
    }

    public void checkPassword(long id, String password) {
        Member member = memberCommonService.findMember(id);
        if (member.getPassword() != password) {
            throw new UnAuthMemberException("wrong password");
        }
    }

    public String checkNickname(long id, String nickname) {
        memberCommonService.isUseableNickname(nickname);
        return nickname;
    }

    public MemberPrintForm modifyNickname(long id, String nickname) throws BadRequestException {
        Member member = memberCommonService.findMember(id);
        memberCommonService.checkCanModify(member);
        memberCommonService.modifiedNickname(member, nickname);
        return MemberPrintForm.toDto(member);
    }

    public MemberPrintForm modifyPassword(long id, String password) {
        Member member = memberCommonService.findMember(id);
        memberCommonService.modifyPassword(member, password);
        return MemberPrintForm.toDto(member);
    }
}
