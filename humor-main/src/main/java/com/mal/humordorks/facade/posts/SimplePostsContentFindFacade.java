package com.mal.humordorks.facade.posts;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.mal.humordorks.dto.PagedResponse;
import com.mal.humordorks.dto.PostsPrintForm;
import com.mal.humordorks.model.PostStatus;
import com.mal.humordorks.model.Posts;
import com.mal.humordorks.service.PostsCommonService;

public class SimplePostsContentFindFacade {

    private final PostsCommonService postsCommonService;

    public SimplePostsContentFindFacade(PostsCommonService postsCommonService) {
        this.postsCommonService = postsCommonService;
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

    private PagedResponse<PostsPrintForm> getPageResponseFromPosts(Page<Posts> pages) {
        List<PostsPrintForm> content = null;
        if (pages.getNumberOfElements() == 0) {
            content = Collections.emptyList();
        } else {
            content = pages.stream()
                    .filter(post -> post.getStatus() != PostStatus.HIDE)
                    .map(post -> PostsPrintForm.toDto(post.getMember().getNickname(), post.getImg(), post.getContent()))
                    .collect(Collectors.toList());
        }
        return new PagedResponse<>(content, pages.getNumber(), pages.getSize(), pages.getTotalElements(),
                pages.getTotalPages(), pages.isLast());
    }

}
