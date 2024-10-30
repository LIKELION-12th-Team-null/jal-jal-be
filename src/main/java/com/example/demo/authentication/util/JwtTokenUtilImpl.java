package com.example.demo.authentication.util;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.demo.authentication.domain.JwtMemberDetail;
import com.example.demo.global.exception.BusinessException;
import com.example.demo.global.response.ApiStatus;
import com.example.demo.member.domain.entity.Member;
import com.example.demo.member.repository.MemberRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class JwtTokenUtilImpl implements JwtTokenUtil{
	private final SecretKey key;
	private final Long accessExpiration;
	private final Long refreshExpiration;
	private final MemberRepository memberRepository;

	private static final String REFRESH_TOKEN_SUBJECT = "refreshToken";
	private static final String GRANT_TYPE = "Bearer ";

	public JwtTokenUtilImpl(@Value("${jwt.token.secret-key}") String secretKey, @Value("${jwt.token.expire.access}") Long accessExpiration,
		@Value("${jwt.token.expire.refresh}") Long refreshExpiration, MemberRepository repository) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.accessExpiration = accessExpiration;
		this.refreshExpiration = refreshExpiration;
		this.memberRepository = repository;
	}

	@Override
	public String generateAccessToken(Authentication authentication) {

		String authority = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		JwtMemberDetail jwtMemberDetail = (JwtMemberDetail) authentication.getPrincipal();

		return GRANT_TYPE + Jwts.builder()
			.subject(authentication.getName())
			.claim("auth", authority)
			.claim("id", jwtMemberDetail.getMemberId())
			.signWith(key)
			.expiration(Date.from(Instant.now().plusMillis(accessExpiration)))
			.compact();
	}

	@Override
	public String generateRefreshToken() {

		return Jwts.builder()
			.subject(REFRESH_TOKEN_SUBJECT)
			.expiration(Date.from(Instant.now().plusMillis(refreshExpiration)))
			.signWith(key)
			.compact();
	}

	@Override
	public Authentication getAuthentication(String accessToken) {

		Optional<Claims> payload = Optional.of(Jwts.parser()
			.verifyWith(key)
			.build()
			.parseSignedClaims(accessToken)
			.getPayload());

		Claims claims = payload.stream()
			.filter(claim -> claim.get("auth") != null)
			.findAny()
			.orElseThrow(() -> new JwtException("error occur from auth"));

		Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
			.map(SimpleGrantedAuthority::new)
			.toList();

		Member sessionMember = memberRepository.findById(Long.parseLong(claims.get("id").toString())).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ApiStatus.MEMBER_NOT_FOUND));

		JwtMemberDetail jwtMemberDetail = JwtMemberDetail.JwtMemberDetailBuilder()
			.authorities(authorities)
			.username(sessionMember.getEmail())
			.memberId(sessionMember.getId())
			.build();

		return new UsernamePasswordAuthenticationToken(jwtMemberDetail, null, authorities);
	}

	@Override
	public Authentication createAuthentication(Member member) {

		Collection<? extends GrantedAuthority> authorities = List.of(
			new SimpleGrantedAuthority("ROLE_" + member.getRole().getRole())
		);

		JwtMemberDetail jwtMemberDetail = JwtMemberDetail.JwtMemberDetailBuilder()
			.authorities(authorities)
			.username(member.getEmail())
			.memberId(member.getId())
			.build();

		return new UsernamePasswordAuthenticationToken(jwtMemberDetail, null, authorities);

	}

	@Override
	public Optional<String> extractAccessToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader("Authorization"))
			.filter(token ->
				token.startsWith(GRANT_TYPE))
			.map(token ->
				token.replace(GRANT_TYPE, ""));
	}

	@Override
	public Optional<String> extractRefreshToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getCookies())
			.stream()
			.flatMap(Arrays::stream)
			.filter(cookie -> cookie.getName().equals("refreshToken"))
			.findFirst()
			.map(Cookie::getValue);
	}

	@Override
	public boolean validate(String token) {
		try {
			Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return false;
	}


}
