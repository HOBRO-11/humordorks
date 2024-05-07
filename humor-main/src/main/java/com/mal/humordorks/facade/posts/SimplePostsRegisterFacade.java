package com.mal.humordorks.facade.posts;

import com.mal.humordorks.dto.PostsPrintForm;
import com.mal.humordorks.dto.PostsRegisterForm;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.service.MemberCommonService;
import com.mal.humordorks.service.PostsCommonService;

public class SimplePostsRegisterFacade {

    private final PostsCommonService postsCommonService;
    private final MemberCommonService memberCommonService;

    public SimplePostsRegisterFacade(PostsCommonService postsCommonService, MemberCommonService memberCommonService) {
        this.postsCommonService = postsCommonService;
        this.memberCommonService = memberCommonService;
    }

    // TODO 기존 이미지들과 비교, 이미지 가공 저장, content안의 텍스트 elastic search에 넣기, 저장된 이미지 추천 목록에 올리기
    public PostsPrintForm registerPosts(long memberId, PostsRegisterForm postsRegisterForm){
        Member member = memberCommonService.findMember(memberId);
        postsCommonService.createPosts(member, postsRegisterForm);
        String nickname = member.getNickname();
        String img = postsRegisterForm.getImg();
        String content = postsRegisterForm.getContent();
        return PostsPrintForm.toDto(nickname, img, content);
    }

}
