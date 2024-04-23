package com.mal.humordorks.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mal.humordorks.model.Comment;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.model.Posts;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByPosts(Posts posts, Pageable pageable);

    Page<Comment> findAllByMember(Member member, Pageable pageable);
    
}
