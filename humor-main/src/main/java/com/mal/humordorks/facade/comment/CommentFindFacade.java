package com.mal.humordorks.facade.comment;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.mal.humordorks.dto.CommentPrintForm;
import com.mal.humordorks.dto.PagedResponse;
import com.mal.humordorks.model.Comment;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.model.Posts;
import com.mal.humordorks.service.CommentCommonService;
import com.mal.humordorks.service.MemberCommonService;
import com.mal.humordorks.service.PostsCommonService;

// TODO Member.nickname Cache가 필요해 보임
public class CommentFindFacade {

    private final PostsCommonService postsCommonService;
private final MemberCommonService memberCommonService;
    private final CommentCommonService commentCommonService;


    public CommentFindFacade(PostsCommonService postsCommonService, MemberCommonService memberCommonService,
            CommentCommonService commentCommonService) {
        this.postsCommonService = postsCommonService;
        this.memberCommonService = memberCommonService;
        this.commentCommonService = commentCommonService;
    }

    public PagedResponse<CommentPrintForm> findCommentOfTheMember(long memberId, int page, int size) {
        Member member = memberCommonService.findMember(memberId);
        Page<Comment> pages = commentCommonService.findCommentsByMember(member, page, size);
        return getPageResponseFromComment(pages);
    }

    public PagedResponse<CommentPrintForm> findCommentOfThePosts(long postsId, int page, int size) {
        Posts posts = postsCommonService.findPosts(postsId);
        Page<Comment> pages = commentCommonService.findCommentsByPosts(posts, page, size);
        return getPageResponseFromComment(pages);
    }

    private PagedResponse<CommentPrintForm> getPageResponseFromComment(Page<Comment> pages) {
        List<CommentPrintForm> content = null;
        if (pages.getNumberOfElements() == 0) {
            content = Collections.emptyList();
        } else {
            content = pages.stream()
                    .map(post -> CommentPrintForm.toDto(post.getMember().getNickname(), post.getContent()))
                    .collect(Collectors.toList());
        }
        return new PagedResponse<>(content, pages.getNumber(), pages.getSize(), pages.getTotalElements(),
                pages.getTotalPages(), pages.isLast());
    }

}
