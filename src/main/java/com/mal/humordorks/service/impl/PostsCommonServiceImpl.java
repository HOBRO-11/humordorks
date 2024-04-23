package com.mal.humordorks.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mal.humordorks.dto.PostsRegisterForm;
import com.mal.humordorks.exception.ResourceNotFound;
import com.mal.humordorks.exception.UnAuthMemberException;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.model.Posts;
import com.mal.humordorks.repository.PostsRepository;
import com.mal.humordorks.service.PostsCommonService;

@Service
public class PostsCommonServiceImpl implements PostsCommonService {

    private final PostsRepository postsRepository;

    public PostsCommonServiceImpl(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    @Override
    public Posts createPosts(Member member, PostsRegisterForm postsRegisterForm) {
        String img = postsRegisterForm.getImg();
        String content = postsRegisterForm.getContent();
        return Posts.createPost(member, img, content);
    }

    @Override
    public Posts findPosts(long id) {
        return postsRepository.findById(id).orElseThrow(() -> new ResourceNotFound("this posts not found"));
    }

    @Override
    public Page<Posts> findAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Direction.DESC, "lastModifiedDate");
        return postsRepository.findAll(pageable);
    }

    // TODO elastic search 를 써서 찾는 인덱스를 만든다.

    @Override
    public void modifiedPosts(Member member, Posts posts, String content) {
        checkAuthor(member, posts);
        posts.modifyPost(content);
    }

    // TODO only just admin can hide posts
    @Override
    public void hidePost(Posts posts){
        posts.hidePost();
    }

    @Override
    public void hidePost(Member member, Posts posts) {
        checkAuthor(member, posts);
        posts.hidePost();
    }

    @Override
    public void publicPost(Member member, Posts posts) {
        checkAuthor(member, posts);
        posts.publicPost();
    }

    @Override
    public void deletePosts(Member member, Posts posts) {
        checkAuthor(member, posts);
        postsRepository.delete(posts);
    }

    private void checkAuthor(Member member, Posts posts) {
        if (posts.getMember() != member) {
            throw new UnAuthMemberException("this member not author");
        }
    }

}
