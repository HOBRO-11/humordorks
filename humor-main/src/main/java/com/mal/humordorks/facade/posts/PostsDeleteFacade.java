package com.mal.humordorks.facade.posts;

import com.mal.humordorks.dto.PostsPrintForm;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.model.Posts;
import com.mal.humordorks.service.MemberCommonService;
import com.mal.humordorks.service.PostsCommonService;

public class PostsDeleteFacade {
    
    private final PostsCommonService postsCommonService;

    private final MemberCommonService memberCommonService;

    public PostsDeleteFacade(PostsCommonService postsCommonService, MemberCommonService memberCommonService) {
        this.postsCommonService = postsCommonService;
        this.memberCommonService = memberCommonService;
    }

    public PostsPrintForm modifyPosts(long memberId, long postsId, String content){
        Posts posts = postsCommonService.findPosts(postsId);
        Member member = memberCommonService.findMember(memberId);
        postsCommonService.modifiedPosts(member, posts, content);
        return PostsPrintForm.toDto(member.getNickname(), posts.getImg(), posts.getContent());
    }
}
