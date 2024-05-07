package com.mal.humordorks.facade.like_posts;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.mal.humordorks.dto.PagedResponse;
import com.mal.humordorks.model.LikePosts;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.service.LikePostsCommonService;
import com.mal.humordorks.service.MemberCommonService;

public class LikePostFindFacade {

    private final MemberCommonService memberCommonService;
    private final LikePostsCommonService likePostsCommonService;
    
    public LikePostFindFacade(MemberCommonService memberCommonService,
            LikePostsCommonService likePostsCommonService) {
        this.memberCommonService = memberCommonService;
        this.likePostsCommonService = likePostsCommonService;
    }

    public PagedResponse<LikePostsPrintForm> findLikePostsByMember(long memberId, int page, int size) {
        Member member = memberCommonService.findMember(memberId);
        Page<LikePosts> likesByMember = likePostsCommonService.findLikesByMember(member, page, size);
        return getPageResponseFromPosts(likesByMember);
    }

    private PagedResponse<LikePostsPrintForm> getPageResponseFromPosts(Page<LikePosts> pages) {
        List<LikePostsPrintForm> content = null;
        if (pages.getNumberOfElements() == 0) {
            content = Collections.emptyList();
        } else {
            content = pages.stream()
                    .map(lp -> LikePostsPrintForm.toDto(lp.getPosts().getImg(), lp.getPosts().getContent()))
                    .collect(Collectors.toList());
        }
        return new PagedResponse<>(content, pages.getNumber(), pages.getSize(), pages.getTotalElements(),
                pages.getTotalPages(), pages.isLast());
    }
}
