package com.mal.humordorks.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.mal.humordorks.exception.ResourceNotFound;
import com.mal.humordorks.exception.UnAuthMemberException;
import com.mal.humordorks.model.LikePosts;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.model.Posts;
import com.mal.humordorks.repository.LikePostsRepository;
import com.mal.humordorks.service.LikePostsCommonService;

public class LikePostsCommonServiceImpl implements LikePostsCommonService {

    private final LikePostsRepository likePostsRepository;

    public LikePostsCommonServiceImpl(LikePostsRepository likePostsRepository) {
        this.likePostsRepository = likePostsRepository;
    }

    @Override
    public LikePosts createLikePosts(Member member, Posts posts) {
        return LikePosts.createLike(posts, member);
    }

    @Override
    public LikePosts findLikePosts(long id) {
        return likePostsRepository.findById(id).orElseThrow(() -> new ResourceNotFound("this likePost not found"));
    }

    @Override
    public Page<LikePosts> findLikesByMember(Member member, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return likePostsRepository.findAllByMember(member, pageable);
    }

    @Override
    public void deleteLikes(Member member, LikePosts likePosts) {
        checkAuthor(member, likePosts);
        likePostsRepository.delete(likePosts);
    }

    private void checkAuthor(Member member, LikePosts likePosts) {
        if (likePosts.getMember() != member) {
            throw new UnAuthMemberException("this member not author");
        }
    }
}
