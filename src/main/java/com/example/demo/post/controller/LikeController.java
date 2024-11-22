package com.example.demo.post.controller;

import com.example.demo.global.response.ApiResponse;
import com.example.demo.post.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ApiResponse<Void> addLike(@PathVariable Integer postId, @RequestParam Long member) {
        likeService.addLike(postId, member);
        return ApiResponse.ok();
    }

    @DeleteMapping
    public ApiResponse<Void> removeLike(@PathVariable Integer postId, @RequestParam Long member) {
        likeService.minusLike(postId, member);
        return ApiResponse.ok();
    }

    @GetMapping("/check")
    public ApiResponse<Boolean> checkLike(@PathVariable Integer postId, @RequestParam Long member) {
        boolean exists = likeService.checkLikeExist(postId, member);
        return ApiResponse.ok(exists);
    }
}
