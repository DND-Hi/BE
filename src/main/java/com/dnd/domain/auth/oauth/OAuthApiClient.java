package com.dnd.domain.auth.oauth;

import com.dnd.domain.auth.dto.request.OauthProvider;

public interface OAuthApiClient {
	OauthProvider oAuthProvider();
	String requestAccessToken(OAuthLoginParams params);

	OAuthInfoResponse requestOAuthInfo(String accessToken);
}
