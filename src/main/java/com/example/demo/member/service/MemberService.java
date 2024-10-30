package com.example.demo.member.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.authentication.util.SecurityContextUtil;
import com.example.demo.global.exception.BusinessException;
import com.example.demo.global.response.ApiStatus;
import com.example.demo.member.domain.NicknameGenerator;
import com.example.demo.member.domain.entity.Member;
import com.example.demo.member.domain.entity.Role;
import com.example.demo.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final SecurityContextUtil securityContextUtil;

	public Map<String, String> getNewNickname() {

		String nickname = NicknameGenerator.generateNickname();

		return Map.of("nickname", nickname);

	}

	public void signUp(String nickname) {

		Member member = memberRepository.findById(securityContextUtil.getContextMemberInfo().getMemberId()).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ApiStatus.MEMBER_NOT_FOUND));

		member.updateNickname(nickname);
		member.updateRole(Role.MEMBER);

	}

}
