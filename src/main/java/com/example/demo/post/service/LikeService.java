package com.example.demo.post.service;

import com.example.demo.member.domain.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.post.domain.entity.PostLike;
import com.example.demo.post.domain.entity.Post;
import com.example.demo.post.repository.LikesRepository;
import com.example.demo.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service

public class LikeService {

    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public LikeService(LikesRepository likesRepository, MemberRepository memberRepository, PostRepository postRepository) {
        this.likesRepository = likesRepository;
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public void minusLike(Integer idx, Long memberId) {
        Post post = postRepository.findById(idx.longValue()).orElse(null);
        if (post != null && post.getLikeCount() > 0) {
            post.setLikeCount(post.getLikeCount() - 1);
            memberRepository.findById(memberId).ifPresent(member ->
                    likesRepository.deleteByMemberAndPost(member, post)
            );
        }
    }

    @Transactional
    public void addLike(Integer idx, Long memberId) {
        Post post = postRepository.findById(idx.longValue()).orElse(null);
        if (post != null) {
            post.setLikeCount(post.getLikeCount() + 1);

            Member member = memberRepository.findById(memberId).orElse(null);
            if (member != null) {
                PostLike like = PostLike.builder()
                        .post(post)
                        .member(member)
                        .build();
                likesRepository.save(like);
            }
        }
    }

    public boolean checkLikeExist(Integer idx, Long memberId) {
        Post post = postRepository.findById(Long.valueOf(idx)).orElse(null);
        Member member = memberRepository.findById(memberId).orElse(null);
        return post != null && !likesRepository.findByMemberAndPost(member, post).isEmpty();
    }
}