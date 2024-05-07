package com.mal.humordorks.service;

import org.springframework.data.domain.Page;

import com.mal.humordorks.model.LikePosts;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.model.Posts;

public interface LikePostsCommonService {

    LikePosts createLikePosts(Member member, Posts posts);

    LikePosts findLikePosts(long id);

    Page<LikePosts> findLikesByMember(Member member, int page, int size);

    void deleteLikes(Member member, LikePosts likePosts);

}