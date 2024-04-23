package com.mal.humordorks.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mal.humordorks.dto.MemberSignUpForm;
import com.mal.humordorks.exception.ResourceNotFound;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.model.MemberStatus;
import com.mal.humordorks.repository.MemberRepository;
import com.mal.humordorks.service.MemberCommonService;

@Service
public class MemberCommonServiceImpl implements MemberCommonService {

    private final MemberRepository memberRepository;

    public MemberCommonServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member createMember(MemberSignUpForm memberSignUpForm) {
        String nickname = memberSignUpForm.getNickname();
        String email = memberSignUpForm.getEmail();
        String password = memberSignUpForm.getPassword();
        return Member.createUser(nickname, email, password);
    }

    @Override
    public Member findMember(long id) {
        return memberRepository.findById(id).orElseThrow(() -> new ResourceNotFound("this member not found"));
    }

    @Override
    public Member findMember(String email){
        return memberRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFound("this member not found"));
    }

    @Override
    public Page<Member> findMembersByNickname(String nickname, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Direction.DESC, "lastModifiedDate");
        return memberRepository.findByNicknameContaining(nickname, pageable);
    }

    @Override
    public Page<Member> findAllMember(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Direction.DESC, "lastModifiedDate");
        return memberRepository.findAll(pageable);
    }

    @Override
    public void modifiedNickname(Member member, String nickname) {
        member.modifyNickname(nickname);
    }

    @Override
    public void modifyPassword(Member member, String password) {
        member.modifyPassword(password);
    }

    @Override
    public void activateMember(Member member) {
        member.activateMember();
    }

    @Override
    public void pauseMember(Member member) {
        MemberStatus status = member.getStatus();
        if (status == MemberStatus.BAN) {
            // TODO 상태에 따른 예외 만들기
        }
        member.pauseMember();
    }

    @Override
    public void banMember(Member member) {
        memberRepository.delete(member);
    }

    @Override
    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }

    @Override
    public boolean isUseableEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public boolean isUseableNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

}
