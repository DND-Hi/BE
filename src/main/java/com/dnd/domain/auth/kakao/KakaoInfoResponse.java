package com.dnd.domain.auth.kakao;

import com.dnd.domain.auth.oauth.OAuthInfoResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.dnd.domain.auth.dto.request.OauthProvider;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoInfoResponse implements OAuthInfoResponse {

	// https://kapi.kakao.com/v2/user/me 요청 결과 값
	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;

	@JsonIgnoreProperties(ignoreUnknown = true)
	private String id;

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class KakaoAccount {
		private KakaoProfile profile;
		private String id;
		private String email;
	}

	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class KakaoProfile {
		private String id;
		private String nickname;
		@JsonProperty("profile_image_url")
		private String profileImageUrl;
	}

	@Override
	public String getSnsId() {
		return id;
	}

	@Override
	public String getEmail() {
		return kakaoAccount.email;
	}

	@Override
	public String getNickname() {
		return kakaoAccount.profile.nickname;
	}

	@Override
	public String getProfileImageUrl() {
		return kakaoAccount.profile.profileImageUrl;
	}

	@Override
	public OauthProvider getOAuthProvider() {
		return OauthProvider.KAKAO;
	}


}
