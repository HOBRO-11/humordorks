package com.mal.humordorks.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "LIKE_POSTS")
public final class LikePosts {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKE_POSTS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "POSTS_ID")
    private Posts posts;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private LikePosts(){}

    public static LikePosts createLike(Posts posts, Member member) {
        LikePosts likePosts = new LikePosts();
        likePosts.posts = posts;
        likePosts.posts.addLikePosts(likePosts);
        likePosts.member = member;
        likePosts.member.addLike(likePosts);
        return likePosts;
    }

}
