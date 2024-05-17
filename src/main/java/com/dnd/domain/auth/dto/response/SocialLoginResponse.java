package com.dnd.domain.auth.dto.response;

import com.dnd.domain.member.domain.Member;

public record SocialLoginResponse(
	Long memberId,
	String accessToken,
	String refreshToken) {

	public static SocialLoginResponse of(
		Member member, TokenPairResponse tokenPairResponse) {
		return new SocialLoginResponse(
			member.getId(),
			tokenPairResponse.accessToken(),
			tokenPairResponse.refreshToken());
	}
}
