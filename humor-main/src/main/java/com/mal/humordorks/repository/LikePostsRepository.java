package com.mal.humordorks.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mal.humordorks.model.LikePosts;
import com.mal.humordorks.model.Member;

public interface LikePostsRepository extends JpaRepository<LikePosts, Long> {
    
    Page<LikePosts> findAllByMember(Member member, Pageable pageable);
}
