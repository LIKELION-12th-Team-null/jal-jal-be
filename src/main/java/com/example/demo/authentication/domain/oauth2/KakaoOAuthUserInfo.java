package com.example.demo.authentication.domain.oauth2;

import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KakaoOAuthUserInfo extends Oauth2UserInfo{

	public KakaoOAuthUserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public Long getId() {
		return (Long) attributes.get("id");
	}

	// email 정보
	@Override
	public Optional<String> getEmail() {
		Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

		if (kakaoAccount == null) return Optional.empty();

		boolean isEmailVerified = (boolean) kakaoAccount.get("is_email_verified");
		boolean isEmailValid = (boolean) kakaoAccount.get("is_email_valid");

		if (!isEmailValid || !isEmailVerified) return Optional.empty();

		return Optional.of((String) kakaoAccount.get("email"));
	}

}
