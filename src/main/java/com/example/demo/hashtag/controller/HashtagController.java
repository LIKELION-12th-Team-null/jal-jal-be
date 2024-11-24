package com.example.demo.hashtag.controller;

import com.example.demo.global.response.ApiResponse;
import com.example.demo.hashtag.domain.Hashtag;
import com.example.demo.hashtag.domain.dto.HashtagResponseDto;
import com.example.demo.hashtag.service.HashtagService;
import com.example.demo.post.domain.entity.Post;
import com.example.demo.post.domain.postDto.PostResponseDto;
import com.example.demo.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/hashtags")
public class HashtagController {

    private final HashtagService hashtagService;
    private final PostService postService;

    @GetMapping()
    public ApiResponse<List<HashtagResponseDto>> getPopularHashtags(){
        List<HashtagResponseDto> popularHashtags = hashtagService.getPopularHashtags();

        return ApiResponse.ok(popularHashtags);
    }


    @GetMapping("/{hashtagName}")
    public ApiResponse<Page<PostResponseDto>> getPostsByHashtag(@PathVariable String hashtagName,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<PostResponseDto> postsByHashtag = hashtagService.getPostsByHashtag(hashtagName, pageRequest);
        return ApiResponse.ok(postsByHashtag);
    }
}