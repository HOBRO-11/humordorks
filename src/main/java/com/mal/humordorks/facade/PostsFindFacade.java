package com.mal.humordorks.facade;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.mal.humordorks.dto.PagedResponse;
import com.mal.humordorks.dto.PostsPrintForm;
import com.mal.humordorks.model.Posts;
import com.mal.humordorks.service.CommentCommonService;
import com.mal.humordorks.service.LikePostsCommonService;
import com.mal.humordorks.service.MemberCommonService;
import com.mal.humordorks.service.PostsCommonService;

public class PostsFindFacade {

    private final PostsCommonService postsCommonService;

    private final MemberCommonService memberCommonService;

    private final LikePostsCommonService likePostsCommonService;

    private final CommentCommonService commentCommonService;

    public PostsFindFacade(PostsCommonService postsCommonService, MemberCommonService memberCommonService,
            LikePostsCommonService likePostsCommonService, CommentCommonService commentCommonService) {
        this.postsCommonService = postsCommonService;
        this.memberCommonService = memberCommonService;
        this.likePostsCommonService = likePostsCommonService;
        this.commentCommonService = commentCommonService;
    }

    public PagedResponse<?> findAllCurrentPosts(int page, int size) {
        Page<Posts> pages = postsCommonService.findAllPosts(page, size);
        List<PostsPrintForm> content = null;
        if (pages.getNumberOfElements() == 0) {
            content = Collections.emptyList();
        } else {
            content = pages.stream().map(post -> PostsPrintForm.toDto(post.getMember().getNickname(), post.getContent(), post.getContent())).collect(Collectors.toList());
        }
        return new PagedResponse<>(content, pages.getNumber(), pages.getSize(), pages.getTotalElements(),
                pages.getTotalPages(), pages.isLast());
    }

    public PagedResponse<?> findAllPosts(int page, int size) {
        Page<Posts> pages = postsCommonService.findAllPosts(page, size);
        List<PostsPrintForm> content = null;
        if (pages.getNumberOfElements() == 0) {
            content = Collections.emptyList();
        } else {
            content = pages.stream().map(post -> PostsPrintForm.toDto(post.getMember().getNickname(), post.getContent(), post.getContent())).collect(Collectors.toList());
        }
        return new PagedResponse<>(content, pages.getNumber(), pages.getSize(), pages.getTotalElements(),
                pages.getTotalPages(), pages.isLast());
    }




}
