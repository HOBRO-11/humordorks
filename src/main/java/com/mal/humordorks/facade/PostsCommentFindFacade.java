package com.mal.humordorks.facade;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.mal.humordorks.dto.CommentPrintForm;
import com.mal.humordorks.dto.PagedResponse;
import com.mal.humordorks.dto.PostsPrintForm;
import com.mal.humordorks.model.Comment;
import com.mal.humordorks.model.Posts;
import com.mal.humordorks.service.CommentCommonService;
import com.mal.humordorks.service.PostsCommonService;

// TODO Member.nickname Cache가 필요해 보임
public class PostsCommentFindFacade {

    private final PostsCommonService postsCommonService;

    private final CommentCommonService commentCommonService;

    public PostsCommentFindFacade(PostsCommonService postsCommonService, CommentCommonService commentCommonService) {
        this.postsCommonService = postsCommonService;
        this.commentCommonService = commentCommonService;
    }

    public PostsPrintForm findPosts(long id) {
        Posts posts = postsCommonService.findPosts(id);
        String nickname = posts.getMember().getNickname();
        String img = posts.getImg();
        String content = posts.getContent();
        return PostsPrintForm.toDto(nickname, img, content);
    }

    public PagedResponse<PostsPrintForm> findAllLatestPosts(int page, int size) {
        Page<Posts> pages = postsCommonService.findAllPosts(page, size);
        return getPageResponseFromPosts(pages);
    }

    public PagedResponse<PostsPrintForm> findAllPosts(int page, int size) {
        Page<Posts> pages = postsCommonService.findAllPosts(page, size);
        return getPageResponseFromPosts(pages);
    }

    public PagedResponse<PostsPrintForm> findPopularWeeklyPosts() {
        Page<Posts> popularWeeklyPosts = postsCommonService.findPopularWeeklyPosts();
        return getPageResponseFromPosts(popularWeeklyPosts);
    }

    public PagedResponse<PostsPrintForm> findPopularMonthlyPosts() {
        Page<Posts> popularWeeklyPosts = postsCommonService.findPopularMonthlyPosts();
        return getPageResponseFromPosts(popularWeeklyPosts);
    }

    public PagedResponse<PostsPrintForm> findPopularYearlyPosts() {
        Page<Posts> popularWeeklyPosts = postsCommonService.findPopularYearlyPosts();
        return getPageResponseFromPosts(popularWeeklyPosts);
    }

    public PagedResponse<CommentPrintForm> findCommentOfThePosts(long postsId, int page, int size) {
        Posts posts = postsCommonService.findPosts(postsId);
        Page<Comment> pages = commentCommonService.findCommentsByPosts(posts, page, size);
        return getPageResponseFromComment(pages);
    }

    private PagedResponse<PostsPrintForm> getPageResponseFromPosts(Page<Posts> pages) {
        List<PostsPrintForm> content = null;
        if (pages.getNumberOfElements() == 0) {
            content = Collections.emptyList();
        } else {
            content = pages.stream()
                    .map(post -> PostsPrintForm.toDto(post.getMember().getNickname(), post.getImg(), post.getContent()))
                    .collect(Collectors.toList());
        }
        return new PagedResponse<>(content, pages.getNumber(), pages.getSize(), pages.getTotalElements(),
                pages.getTotalPages(), pages.isLast());
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
