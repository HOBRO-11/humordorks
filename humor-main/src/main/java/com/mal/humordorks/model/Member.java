package com.mal.humordorks.model;

import java.util.List;
import java.util.Vector;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mal.humordorks.model.common.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
@Table(name = "MEMBER")
public final class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Size(min = 2, max = 10)
    @Column(name = "NICKNAME", unique = true)
    private String nickname;

    @Email
    @Column(name = "EMAIL", unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "PASSWORD")
    private String password;

    @Enumerated(value =  EnumType.STRING)
    @Column(name = "STATUS")
    private MemberStatus status = MemberStatus.ACTIVATE;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<LikePosts> likePosts = new Vector<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new Vector<>();

    @OneToMany(mappedBy = "member")
    private List<Posts> posts = new Vector<>();

    private Member() {
    }

    public static Member createUser(@NotBlank String nickname, @Email String email, String password) {
        Member member = new Member();
        member.nickname = nickname;
        member.email = email;
        member.password = password;
        return member;
    }

    public void modifyNickname(String nickname) {
        this.nickname = nickname;
    }

    public void modifyPassword(String password) {
        this.password = password;
    }

    public void addLike(LikePosts likePosts) {
        this.likePosts.add(likePosts);
    }

    public void addComments(Comment comment) {
        this.comments.add(comment);
    }

    public void addPost(Posts posts) {
        this.posts.add(posts);
    }

    public void activateMember() {
        this.status = MemberStatus.ACTIVATE;
    }

    public void pauseMember() {
        this.status = MemberStatus.PAUSE;
    }

    public void banMember() {
        this.status = MemberStatus.BAN;
    }
}
