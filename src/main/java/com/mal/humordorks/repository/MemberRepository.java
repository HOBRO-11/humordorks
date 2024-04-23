package com.mal.humordorks.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mal.humordorks.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Page<Member> findByNicknameContaining(String nickname, Pageable pageable);

	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);
    
}
