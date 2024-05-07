package com.mal.humordorks.service;

import org.springframework.data.domain.Page;

import com.mal.humordorks.model.Comment;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.model.Posts;

public interface CommentCommonService {

    Comment createComment(Member member, Posts posts, String content);

    Comment findComment(long id);

    Page<Comment> findCommentsByPosts(Posts posts, int page, int size);

    Page<Comment> findCommentsByMember(Member member, int page, int size);

    void modifyComment(Member member, Comment comment, String content);

    void deleteComment(Member member, Comment comment);

    void hideComment(Comment comment);

    void publicComment(Comment comment);

}