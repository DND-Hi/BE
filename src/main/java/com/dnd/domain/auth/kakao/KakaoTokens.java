package com.dnd.domain.auth.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoTokens {

	// https://kauth.kakao.com/oauth/token 요청 결과 값
	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("expires_in")
	private Integer expiresIn;

	@JsonProperty("refresh_token_expires_in")
	private Integer refreshTokenExpiresIn;

	@JsonProperty("scope")
	private String scope;
}
