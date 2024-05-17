package com.dnd.domain.auth.service;

import org.springframework.stereotype.Service;

import com.dnd.domain.auth.dto.request.OauthProvider;
import com.dnd.domain.auth.dto.response.SocialLoginResponse;
import com.dnd.domain.auth.oauth.OAuthInfoResponse;
import com.dnd.domain.auth.oauth.OAuthLoginParams;
import com.dnd.domain.member.dao.MemberRepository;
import com.dnd.domain.member.domain.Member;
import com.dnd.domain.member.domain.OauthInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final AuthTokensGenerator authTokensGenerator;
	private final RequestOAuthInfoService requestOAuthInfoService;

	public SocialLoginResponse socialLoginMember(OAuthLoginParams params) {
		OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
		Member member = findOrCreateMember(oAuthInfoResponse, params.oAuthProvider());
		member.updateLastLoginAt();
		return authTokensGenerator.generate(member);
	}

	public SocialLoginResponse socialLoginMemberKakaoToken(String kakaoToken) {
		OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.requestKakaoToken(kakaoToken);
		Member member = findOrCreateMember(oAuthInfoResponse, OauthProvider.KAKAO);
		member.updateLastLoginAt();
		return authTokensGenerator.generate(member);
	}

	private Member findOrCreateMember(OAuthInfoResponse oAuthInfoResponse, OauthProvider provider) {
		return memberRepository.findByNicknameAndOauthInfo_OauthId(oAuthInfoResponse.getNickname(), oAuthInfoResponse.getSnsId())
			.orElseGet(() -> signUp(oAuthInfoResponse, provider));
	}

	private Member signUp(OAuthInfoResponse oAuthInfoResponse, OauthProvider provider) {
		OauthInfo oauthInfo = OauthInfo.createOauthInfo(oAuthInfoResponse.getSnsId(),
			provider.getValue(), oAuthInfoResponse.getEmail());
		Member user = Member.createNormalMember(oauthInfo, oAuthInfoResponse.getNickname(), oAuthInfoResponse.getProfileImageUrl());
		return memberRepository.save(user);
	}
}
