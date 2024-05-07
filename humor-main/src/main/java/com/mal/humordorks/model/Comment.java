package com.mal.humordorks.model;

import com.mal.humordorks.model.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "COMMENT")
public final class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "POSTS_ID")
    private Posts posts;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "status")
    @Enumerated
    private CommentStatus status = CommentStatus.PUBLIC;

    private Comment() {
    }

    public static Comment createComment(Member member, Posts posts, String content) {
        Comment comment = new Comment();
        comment.member = member;
        comment.member.addComments(comment);
        comment.posts = posts;
        comment.posts.addComment(comment);
        comment.content = content;
        return comment;
    }

    public void modifyContent(String content) {
        this.content = content;
    }

    public void hideComment() {
        this.status = CommentStatus.HIDE;
    }

    public void publicComment() {
        this.status = CommentStatus.PUBLIC;
    }
}
