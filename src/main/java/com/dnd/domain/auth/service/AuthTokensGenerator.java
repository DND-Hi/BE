package com.dnd.domain.auth.service;


import org.springframework.stereotype.Component;

import com.dnd.domain.auth.dto.response.SocialLoginResponse;
import com.dnd.domain.auth.dto.response.TokenPairResponse;
import com.dnd.domain.member.domain.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuthTokensGenerator {
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60
			* 60 * 24;            // 1일
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60
			* 60 * 24 * 7;  // 7일

	private final TokenService tokenService;

	public SocialLoginResponse generate(Member member) {

		return SocialLoginResponse.of(
				member,
				TokenPairResponse.from(
					tokenService.generateToken(member, ACCESS_TOKEN_EXPIRE_TIME),
					tokenService.generateRefreshToken(member, REFRESH_TOKEN_EXPIRE_TIME)
				));
	}

}
