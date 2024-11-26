package com.example.demo.authentication.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtMemberDetail extends User {

	// member 의 PK 값
	private Long memberId;

	@Builder(builderMethodName = "JwtMemberDetailBuilder")
	public JwtMemberDetail(String username, Collection<? extends GrantedAuthority> authorities, Long memberId) {
		super(username, "", authorities);
		this.memberId = memberId;
	}
}
