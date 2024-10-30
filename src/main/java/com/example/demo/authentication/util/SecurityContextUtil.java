package com.example.demo.authentication.util;

import com.example.demo.authentication.domain.JwtMemberDetail;

public interface SecurityContextUtil {

	JwtMemberDetail getContextMemberInfo();

}
