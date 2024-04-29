package com.mal.humordorks.facade.posts;

import com.mal.humordorks.dto.PostsPrintForm;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.model.Posts;
import com.mal.humordorks.service.MemberCommonService;
import com.mal.humordorks.service.PostsCommonService;

public class PostsModifyFacade {
    
    private final PostsCommonService postsCommonService;

    private final MemberCommonService memberCommonService;

    public PostsModifyFacade(PostsCommonService postsCommonService, MemberCommonService memberCommonService) {
        this.postsCommonService = postsCommonService;
        this.memberCommonService = memberCommonService;
    }

    public PostsPrintForm modifyPosts(long memberId, long postsId, String content){
        Posts posts = postsCommonService.findPosts(postsId);
        Member member = memberCommonService.findMember(memberId);
        postsCommonService.modifiedPosts(member, posts, content);
        return PostsPrintForm.toDto(member.getNickname(), posts.getImg(), posts.getContent());
    }

    public PostsPrintForm hidePosts(long postsId){
        Posts posts = postsCommonService.findPosts(postsId);
        postsCommonService.hidePost(posts);
        return PostsPrintForm.toDto(posts.getMember().getNickname(), null, null); 
        //TODO 작성자에 의해 숨겨진 게시글 입니다.
    }

    public PostsPrintForm publicPosts(long postsId){
        Posts posts = postsCommonService.findPosts(postsId);
        postsCommonService.publicPost(posts);
        Member member = posts.getMember();
        return PostsPrintForm.toDto(member.getNickname(), posts.getImg(), posts.getContent());
    }

}
