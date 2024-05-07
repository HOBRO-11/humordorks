package com.mal.humordorks.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.mal.humordorks.exception.ResourceNotFound;
import com.mal.humordorks.exception.UnAuthMemberException;
import com.mal.humordorks.model.Comment;
import com.mal.humordorks.model.Member;
import com.mal.humordorks.model.Posts;
import com.mal.humordorks.repository.CommentRepository;
import com.mal.humordorks.service.CommentCommonService;

public class CommentCommonServiceImpl implements CommentCommonService {

    private final CommentRepository commentRepository;

    public CommentCommonServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment createComment(Member member, Posts posts, String content) {
        Comment comment = Comment.createComment(member, posts, content);
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public Comment findComment(long id) {
        return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFound("this comment not found"));
    }

    @Override
    public Page<Comment> findCommentsByPosts(Posts posts, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Direction.ASC, "createdDate");
        return commentRepository.findAllByPosts(posts, pageable);
    }

    @Override
    public void publicComment(Comment comment) {
        comment.publicComment();
    }

    @Override
    public Page<Comment> findCommentsByMember(Member member, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findAllByMember(member, pageable);
    }

    @Override
    public void modifyComment(Member member, Comment comment, String content) {
        checkAuthor(member, comment);
        comment.modifyContent(content);
    }

    @Override
    public void hideComment(Comment comment) {
        comment.hideComment();
    }

    @Override
    public void deleteComment(Member member, Comment comment) {
        checkAuthor(member, comment);
        commentRepository.delete(comment);
    }

    private void checkAuthor(Member member, Comment comment) {
        if (comment.getMember() != member) {
            throw new UnAuthMemberException("this member not author");
        }
    }

}
