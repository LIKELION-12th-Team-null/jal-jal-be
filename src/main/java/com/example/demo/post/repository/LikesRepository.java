package com.example.demo.post.repository;

import com.example.demo.member.domain.entity.Member;
import com.example.demo.post.domain.entity.PostLike;
import com.example.demo.post.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<PostLike, Long> {
    List<PostLike> findByMemberAndPost(Member member, Post post);
    void deleteByMemberAndPost(Member member, Post post);
}
