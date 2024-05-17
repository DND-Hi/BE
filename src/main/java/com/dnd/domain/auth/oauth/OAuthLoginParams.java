package com.dnd.domain.auth.oauth;

import org.springframework.util.MultiValueMap;

import com.dnd.domain.auth.dto.request.OauthProvider;

public interface OAuthLoginParams {
	OauthProvider oAuthProvider();
	MultiValueMap<String, String> makeBody();
}
