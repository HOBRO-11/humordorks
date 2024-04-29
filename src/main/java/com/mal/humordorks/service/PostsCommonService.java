package com.mal.humordorks.service;

import org.springframework.data.domain.Page;

import com.mal.humordorks.dto.PostsRegisterForm;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.model.Posts;

public interface PostsCommonService {

    Posts createPosts(Member member, PostsRegisterForm postsRegisterForm);

    Posts findPosts(long id);

    Page<Posts> findAllPosts(int page, int size);

    Page<Posts> findPopularWeeklyPosts();

    Page<Posts> findPopularMonthlyPosts();

    Page<Posts> findPopularYearlyPosts();

    void modifiedPosts(Member member, Posts posts, String content);

    // TODO elastic search 를 써서 찾는 인덱스를 만든다.

    // TODO only just admin can hide posts
    void hidePost(Posts posts);

    void publicPost(Posts posts);

    void deletePosts(Member member, Posts posts);

}