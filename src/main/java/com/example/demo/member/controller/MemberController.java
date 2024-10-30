package com.example.demo.member.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.global.response.ApiResponse;
import com.example.demo.member.domain.dto.request.SignUpRequest;
import com.example.demo.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/members/nickname")
	public ApiResponse<Map<String, String>> getRandomNickname() {

		return ApiResponse.ok(memberService.getNewNickname());
	}

	@PutMapping("/members")
	public ApiResponse<Void> signUp(@RequestBody SignUpRequest request) {

		memberService.signUp(request.getNickname());

		return ApiResponse.ok();
	}
}
