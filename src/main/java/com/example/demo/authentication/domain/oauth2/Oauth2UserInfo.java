package com.example.demo.authentication.domain.oauth2;

import java.util.Map;
import java.util.Optional;

public abstract class Oauth2UserInfo {

	protected Map<String, Object> attributes;

	public Oauth2UserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public abstract Long getId();

	public abstract Optional<String> getEmail();

}
