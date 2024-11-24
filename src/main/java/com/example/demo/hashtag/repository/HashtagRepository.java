package com.example.demo.hashtag.repository;

import com.example.demo.hashtag.domain.Hashtag;
import com.example.demo.hashtag.domain.PostHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findByHashtagName(String HashtagName);
}
