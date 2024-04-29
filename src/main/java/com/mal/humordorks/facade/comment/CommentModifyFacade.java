package com.mal.humordorks.facade.comment;

import com.mal.humordorks.dto.CommentPrintForm;
import com.mal.humordorks.model.Comment;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.service.CommentCommonService;
import com.mal.humordorks.service.MemberCommonService;

public class CommentModifyFacade {
    
    private final CommentCommonService commentCommonService;

    private final MemberCommonService memberCommonService;


    public CommentModifyFacade(CommentCommonService commentCommonService, MemberCommonService memberCommonService) {
        this.commentCommonService = commentCommonService;
        this.memberCommonService = memberCommonService;
    }

    public CommentPrintForm modifyComment(long memberId, long commentId, String content){
        Comment comment = commentCommonService.findComment(commentId);
        Member member = memberCommonService.findMember(memberId);
        commentCommonService.modifyComment(member, comment, content);
        return CommentPrintForm.toDto(member.getNickname(), content);
    }

    public CommentPrintForm hideComment(long commentId){
        Comment comment = commentCommonService.findComment(commentId);
        commentCommonService.hideComment(comment);
        return CommentPrintForm.toDto(comment.getMember().getNickname(), null);
    }

    public CommentPrintForm publicComment(long commentId){
        Comment comment = commentCommonService.findComment(commentId);
        commentCommonService.publicComment(comment);
        return CommentPrintForm.toDto(comment.getMember().getNickname(), comment.getContent());
    }


}
