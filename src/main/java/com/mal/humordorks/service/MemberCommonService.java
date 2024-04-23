package com.mal.humordorks.service;

import org.springframework.data.domain.Page;

import com.mal.humordorks.dto.MemberSignUpForm;
import com.mal.humordorks.model.Member;

public interface MemberCommonService {

    Member createMember(MemberSignUpForm memberSignUpForm);

    Member findMember(long id);

    Member findMember(String email);
    
    Page<Member> findMembersByNickname(String nickname, int page, int size);

    Page<Member> findAllMember(int page, int size);

    void modifiedNickname(Member member, String nickname);

    void modifyPassword(Member member, String password);

    void activateMember(Member member);

    void pauseMember(Member member);

    void banMember(Member member);

    void deleteMember(Member member);

    boolean isUseableEmail(String email);

    boolean isUseableNickname(String nickname);

}