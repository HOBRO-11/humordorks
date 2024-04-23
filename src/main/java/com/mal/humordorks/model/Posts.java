package com.mal.humordorks.model;

import java.util.List;
import java.util.Vector;

import org.hibernate.validator.constraints.URL;

import com.mal.humordorks.model.common.BaseTimeEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "POSTS")
public final class Posts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POSTS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @URL
    @Column(name = "IMG")
    private String img;

    @Column(name = "CONTENT")
    private String content;

    @OneToMany(mappedBy = "posts")
    private List<Comment> comments = new Vector<>();

    @OneToMany(mappedBy = "posts", cascade = CascadeType.REMOVE)
    private List<LikePosts> likePosts = new Vector<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private PostStatus status;

    private Posts() {
    }

    public static Posts createPost(Member member, @URL String img, String content) {
        Posts posts = new Posts();
        posts.member = member;
        posts.img = img;
        posts.content = content;
        posts.status = PostStatus.PUBLIC;
        return posts;
    }

    public void modifyPost(String content) {
        this.content = content;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void addLike(LikePosts likePosts) {
        this.likePosts.add(likePosts);
    }

    public void hidePost() {
        this.status = PostStatus.HIDE;
    }

    public void publicPost() {
        this.status = PostStatus.PUBLIC;
    }

}
