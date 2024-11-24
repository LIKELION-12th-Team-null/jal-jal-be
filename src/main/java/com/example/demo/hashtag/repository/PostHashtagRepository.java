package com.example.demo.hashtag.repository;

import com.example.demo.hashtag.domain.Hashtag;
import com.example.demo.hashtag.domain.PostHashtag;
import com.example.demo.post.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostHashtagRepository extends JpaRepository<PostHashtag, Long> {

    @Query("SELECT ph.post FROM PostHashtag ph WHERE ph.hashtag.hashtagName = :hashtagName")
    Page<Post> findPostsByHashtagName(String hashtagName, Pageable pageable);
}
