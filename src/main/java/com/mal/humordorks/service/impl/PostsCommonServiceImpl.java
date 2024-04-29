package com.mal.humordorks.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.YearMonth;

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

    @Override
    public Page<Posts> findPopularWeeklyPosts() {
        Pageable pageable = PageRequest.of(0, 100, Direction.DESC, "likes");
        LocalDateTime mondayOfWeek = getMondayOfTheWeek();
        LocalDateTime sundayOfWeek = mondayOfWeek.plusDays(6L);
        return postsRepository.findPopularPosts(mondayOfWeek, sundayOfWeek, pageable);
    }

    @Override
    public Page<Posts> findPopularMonthlyPosts() {
        Pageable pageable = PageRequest.of(0, 100, Direction.DESC, "likes");
        LocalDateTime firstDayOfMonth = getFirstDayOfMonth();
        LocalDateTime lastDayOfMonth = getLastDayOfMonth();

        return postsRepository.findPopularPosts(firstDayOfMonth, lastDayOfMonth, pageable);
    }

    @Override
    public Page<Posts> findPopularYearlyPosts() {
        Pageable pageable = PageRequest.of(0, 100, Direction.DESC, "likes");
        LocalDateTime firstDayOfYear = getFirstDayOfYear();
        LocalDateTime lastDayOfYear = getLastDayOfYear();

        return postsRepository.findPopularPosts(firstDayOfYear, lastDayOfYear, pageable);
    }

    // TODO elastic search 를 써서 찾는 인덱스를 만든다.

    @Override
    public void modifiedPosts(Member member, Posts posts, String content) {
        checkAuthor(member, posts);
        posts.modifyPost(content);
    }

    // TODO only just admin can hide posts
    @Override
    public void hidePost(Posts posts) {
        posts.hidePost();
    }

    @Override
    public void publicPost(Posts posts) {
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

    private LocalDateTime getMondayOfTheWeek() {
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        LocalDateTime mondayOfWeek = null;
        while (dayOfWeek != DayOfWeek.MONDAY) {
            mondayOfWeek = now.minusDays(1L);
        }
        return mondayOfWeek;
    }

    private LocalDateTime getFirstDayOfMonth() {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        YearMonth yearMonth = YearMonth.of(year, month);
        int lengthOfMonth = yearMonth.lengthOfMonth();
        return LocalDateTime.of(year, month, lengthOfMonth, 0, 0);
    }

    private LocalDateTime getLastDayOfMonth() {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        YearMonth yearMonth = YearMonth.of(year, month);
        int lengthOfMonth = yearMonth.lengthOfMonth();
        return LocalDateTime.of(year, month, lengthOfMonth, 23, 59);
    }

    private LocalDateTime getFirstDayOfYear() {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        return LocalDateTime.of(year, 1, 1, 0, 0);
    }

    private LocalDateTime getLastDayOfYear() {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        return LocalDateTime.of(year, 12, 31, 23, 59);
    }



}
