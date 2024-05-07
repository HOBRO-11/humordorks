package com.mal.humordorks.facade.like_posts;

import com.mal.humordorks.model.Member;
import com.mal.humordorks.model.Posts;
import com.mal.humordorks.service.LikePostsCommonService;
import com.mal.humordorks.service.MemberCommonService;
import com.mal.humordorks.service.PostsCommonService;

public class LikePostsRegisterFacade {

    private final MemberCommonService memberCommonService;
    private final PostsCommonService postsCommonService;
    private final LikePostsCommonService likePostsCommonService;

    public LikePostsRegisterFacade(MemberCommonService memberCommonService, PostsCommonService postsCommonService,
            com.mal.humordorks.service.LikePostsCommonService likePostsCommonService) {
        this.memberCommonService = memberCommonService;
        this.postsCommonService = postsCommonService;
        this.likePostsCommonService = likePostsCommonService;
    }

    public void createLikePosts(long memberId, long postsId) {
        Member member = memberCommonService.findMember(postsId);
        Posts posts = postsCommonService.findPosts(postsId);
        likePostsCommonService.createLikePosts(member, posts);
    }
}
