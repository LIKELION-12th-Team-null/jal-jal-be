package com.example.demo.authentication.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.demo.authentication.domain.JwtMemberDetail;

import lombok.Getter;

@Getter
@Component
public class SimpleSecurityContextUtil implements SecurityContextUtil {

	@Override
	public JwtMemberDetail getContextMemberInfo() {
		return (JwtMemberDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
