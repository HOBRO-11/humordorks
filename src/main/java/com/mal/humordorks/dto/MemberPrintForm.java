package com.mal.humordorks.dto;

import com.mal.humordorks.model.Member;

import lombok.Data;

@Data
public class MemberPrintForm {

    String email;
    String nickname;

    public static MemberPrintForm toDto(Member member) {
        MemberPrintForm memberDto = new MemberPrintForm();
        memberDto.email = member.getEmail();
        memberDto.nickname = member.getNickname();
        return memberDto;
    }

}
