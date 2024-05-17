package com.dnd.domain.auth.dto.response;

import com.dnd.domain.member.domain.Member;

public record SocialLoginResponse(
	Long memberId,
	String accessToken,
	String refreshToken,
	String nickname,
	String profileImageUrl,
	String email
	) {

	public static SocialLoginResponse of(
		Member member, TokenPairResponse tokenPairResponse) {
		return new SocialLoginResponse(
			member.getId(),
			tokenPairResponse.accessToken(),
			tokenPairResponse.refreshToken(),
			member.getNickname(),
			member.getProfileImage(),
			member.getOauthInfo().getOauthEmail()
		);
	}
}
