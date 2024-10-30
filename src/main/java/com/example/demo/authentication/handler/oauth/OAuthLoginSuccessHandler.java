package com.example.demo.authentication.handler.oauth;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.authentication.domain.oauth2.CustomOAuth2User;
import com.example.demo.authentication.util.JwtTokenUtil;
import com.example.demo.global.exception.BusinessException;
import com.example.demo.global.response.ApiStatus;
import com.example.demo.member.domain.entity.Member;
import com.example.demo.member.domain.entity.Role;
import com.example.demo.member.repository.MemberRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtTokenUtil jwtTokenUtil;
	private final MemberRepository memberRepository;

	@Value("${jwt.cookie.expire}")
	private Integer COOKIE_EXPIRATION;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		CustomOAuth2User oAuthUser = (CustomOAuth2User) authentication.getPrincipal();

		Member member = memberRepository.findById(oAuthUser.getMember().getId()).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ApiStatus.MEMBER_NOT_FOUND));

		Authentication authentication1 = jwtTokenUtil.createAuthentication(member);

		String accessToken = jwtTokenUtil.generateAccessToken(authentication1);
		String refreshToken = jwtTokenUtil.generateRefreshToken();

		member.updateRefreshToken(refreshToken);

		ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
			.path("/")
			.httpOnly(true)
			.sameSite("None")
			.maxAge(COOKIE_EXPIRATION)
			.secure(true)
			.build();

		response.setHeader("Set-Cookie", cookie.toString());

		String redirectUrl = "http://localhost:3000/oauth/callback/main?token=" +
			URLEncoder.encode(accessToken, StandardCharsets.UTF_8);

		if (member.getRole() == Role.SOCIAL) {
			redirectUrl = "http://localhost:3000/oauth/callback/sign-up?token=" +
				URLEncoder.encode(accessToken, StandardCharsets.UTF_8);
		}

		response.sendRedirect(redirectUrl);
	}
}
