package com.dnd.domain.auth.oauth;

import com.dnd.domain.auth.dto.request.OauthProvider;

public interface OAuthInfoResponse {
	String getSnsId();
	String getEmail();
	String getNickname();
	String getProfileImageUrl();
	OauthProvider getOAuthProvider();
}
