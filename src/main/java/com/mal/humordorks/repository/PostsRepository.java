package com.mal.humordorks.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mal.humordorks.model.Posts;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    
    @Query("SELECT p FROM Posts p WHERE p.created_date >= :start_date AND p.created_date <= :end_date")
    Page<Posts> findPopularPosts(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);


}
