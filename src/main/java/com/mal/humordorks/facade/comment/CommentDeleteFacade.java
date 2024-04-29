package com.mal.humordorks.facade.comment;

import com.mal.humordorks.model.Comment;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.service.CommentCommonService;
import com.mal.humordorks.service.MemberCommonService;

public class CommentDeleteFacade {

    private final CommentCommonService commentCommonService;
    private final MemberCommonService memberCommonService;

    public CommentDeleteFacade(CommentCommonService commentCommonService, MemberCommonService memberCommonService) {
        this.commentCommonService = commentCommonService;
        this.memberCommonService = memberCommonService;
    }

    public void deleteComment(long memberId, long commentId){
        Member member = memberCommonService.findMember(commentId);
        Comment comment = commentCommonService.findComment(commentId);
        commentCommonService.deleteComment(member, comment);
    }
}
