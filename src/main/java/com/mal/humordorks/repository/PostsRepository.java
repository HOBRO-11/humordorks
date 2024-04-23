package com.mal.humordorks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mal.humordorks.model.Posts;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    
}
