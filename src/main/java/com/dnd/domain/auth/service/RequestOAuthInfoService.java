package com.dnd.domain.auth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dnd.domain.auth.dto.request.OauthProvider;
import com.dnd.domain.auth.oauth.OAuthApiClient;
import com.dnd.domain.auth.oauth.OAuthInfoResponse;
import com.dnd.domain.auth.oauth.OAuthLoginParams;

@Component
public class RequestOAuthInfoService {
	private final Map<OauthProvider, OAuthApiClient> clients = new HashMap<>();

	public RequestOAuthInfoService(List<OAuthApiClient> clients) {
		if (clients == null) {
			throw new IllegalArgumentException("Clients cannot be null");
		}

		for (OAuthApiClient client : clients) {
			this.clients.put(client.oAuthProvider(), client);
		}
	}

	public OAuthInfoResponse request(OAuthLoginParams params) {
		if (params.oAuthProvider() == null) {
			throw new IllegalArgumentException("OAuth provider must be specified");
		}

		OAuthApiClient client = clients.get(params.oAuthProvider());
		if (client == null) {
			throw new IllegalArgumentException("No client found for the given OAuth provider");
		}

		String accessToken = client.requestAccessToken(params);
		return client.requestOAuthInfo(accessToken);
	}
}