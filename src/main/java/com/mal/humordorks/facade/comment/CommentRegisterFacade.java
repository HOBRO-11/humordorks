package com.mal.humordorks.facade.comment;

import com.mal.humordorks.dto.CommentPrintForm;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.model.Posts;
import com.mal.humordorks.service.CommentCommonService;
import com.mal.humordorks.service.MemberCommonService;
import com.mal.humordorks.service.PostsCommonService;

public class CommentRegisterFacade {

    private final CommentCommonService commentCommonService;
    private final PostsCommonService postsCommonService;
    private final MemberCommonService memberCommonService;

    public CommentRegisterFacade(CommentCommonService commentCommonService, PostsCommonService postsCommonService,
            MemberCommonService memberCommonService) {
        this.commentCommonService = commentCommonService;
        this.postsCommonService = postsCommonService;
        this.memberCommonService = memberCommonService;
    }

    public CommentPrintForm createComment(long memberId, long postsId, String content){
        Member member = memberCommonService.findMember(memberId);
        Posts posts = postsCommonService.findPosts(postsId);
        commentCommonService.createComment(member, posts, content);
        return CommentPrintForm.toDto(member.getNickname(), content);
    }
}
