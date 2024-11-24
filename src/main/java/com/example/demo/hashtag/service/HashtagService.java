package com.example.demo.hashtag.service;

import com.example.demo.global.exception.BusinessException;
import com.example.demo.hashtag.domain.Hashtag;
import com.example.demo.hashtag.domain.PostHashtag;
import com.example.demo.hashtag.domain.dto.HashtagResponseDto;
import com.example.demo.hashtag.repository.PostHashtagRepository;
import com.example.demo.post.domain.entity.Post;
import com.example.demo.post.domain.postDto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HashtagService {

    private final PostHashtagRepository postHashtagRepository;

    public List<HashtagResponseDto> getPopularHashtags(){
        List<PostHashtag> postHashtags = postHashtagRepository.findAll();

        Map<Hashtag, Long> hashtagCountMap = new HashMap<>();

        for (PostHashtag postHashtag : postHashtags) {
            Hashtag hashtag = postHashtag.getHashtag();
            hashtagCountMap.put(hashtag, hashtagCountMap.getOrDefault(hashtag, 0L) + 1);
        }

        List<HashtagResponseDto> popularHashtags = hashtagCountMap.entrySet().stream()
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                .map(entry -> new HashtagResponseDto(entry.getKey()))
                .collect(Collectors.toList());

        return popularHashtags;
    }

    public Page<PostResponseDto> getPostsByHashtag(String hashtagName, Pageable pageable){
        Page<Post> postsByHashtagName = postHashtagRepository.findPostsByHashtagName(hashtagName, pageable);
        return postsByHashtagName.map(post -> new PostResponseDto(post));
    }
}
