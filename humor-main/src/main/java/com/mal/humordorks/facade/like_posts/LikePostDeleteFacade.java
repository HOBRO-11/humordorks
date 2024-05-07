package com.mal.humordorks.facade.like_posts;

import com.mal.humordorks.model.LikePosts;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.service.LikePostsCommonService;
import com.mal.humordorks.service.MemberCommonService;

public class LikePostDeleteFacade {

    private final MemberCommonService memberCommonService;
    private final LikePostsCommonService likePostsCommonService;

    public LikePostDeleteFacade(MemberCommonService memberCommonService,
            LikePostsCommonService likePostsCommonService) {
        this.memberCommonService = memberCommonService;
        this.likePostsCommonService = likePostsCommonService;
    }

    public void deleteLikePosts(long memberId, long likePostsId) {
        Member member = memberCommonService.findMember(memberId);
        LikePosts likePosts = likePostsCommonService.findLikePosts(likePostsId);
        likePostsCommonService.deleteLikes(member, likePosts);
    }

}
