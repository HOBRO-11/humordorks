package com.mal.humordorks.facade;

import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.coyote.BadRequestException;

import com.mal.humordorks.dto.MemberPrintForm;
import com.mal.humordorks.exception.ResourceExistException;
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
        LocalDateTime lastModifiedDate = member.getLastModifiedDate();
        long days = Duration.between(lastModifiedDate, LocalDateTime.now()).toDays();
        if(days < 90){
            throw new BadRequestException("you can change nickname after 90 days from last Modified Date");
        }
        member.modifyNickname(nickname);
        return MemberPrintForm.toDto(member);
    }

    public MemberPrintForm modifyPassword(long id, String password) {
        Member member = memberCommonService.findMember(id);
        if (member.getPassword() == password) {
            throw new ResourceExistException("Previously used passwords cannot be used.");
        }
        member.modifyPassword(password);
        return MemberPrintForm.toDto(member);
    }
}
