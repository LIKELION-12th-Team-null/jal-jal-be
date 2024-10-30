package com.example.demo.authentication.domain.oauth2;

import java.util.Map;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.global.exception.BusinessException;
import com.example.demo.global.response.ApiStatus;
import com.example.demo.member.domain.entity.Member;
import com.example.demo.member.domain.entity.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoOAuthAttribute {

	private String nameAttributeKey;
	private Oauth2UserInfo oauth2UserInfo;
	private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	@Builder
	private KakaoOAuthAttribute(String nameAttributeKey, Oauth2UserInfo oauth2UserInfo) {
		this.nameAttributeKey = nameAttributeKey;
		this.oauth2UserInfo = oauth2UserInfo;
	}

	public KakaoOAuthAttribute(String nameAttributeKey) {
		this.nameAttributeKey = nameAttributeKey;
	}

	public static KakaoOAuthAttribute of(String nameAttributeKey, Map<String, Object> attribute) {
		return KakaoOAuthAttribute.builder()
			.nameAttributeKey(nameAttributeKey)
			.oauth2UserInfo(new KakaoOAuthUserInfo(attribute))
			.build();
	}

	public Member toEntity(Oauth2UserInfo kakaoOAuthUserInfo) {
		return Member.builder()
			.email(kakaoOAuthUserInfo.getEmail().orElseThrow(() -> new BusinessException(ApiStatus.OAUTH_ATTRIBUTE_ERROR)))
			.socialId(kakaoOAuthUserInfo.getId())
			.role(Role.SOCIAL)
			.build();
	}
}
