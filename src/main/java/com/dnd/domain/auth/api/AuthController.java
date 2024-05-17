package com.dnd.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.domain.auth.dto.response.SocialLoginResponse;
import com.dnd.domain.auth.kakao.KakaoLoginParams;
import com.dnd.domain.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/kakao")
	public ResponseEntity<SocialLoginResponse> memberSocialLogin(
		@RequestBody KakaoLoginParams params
		) {
		SocialLoginResponse response = authService.socialLoginMember(params);

		// 서버 쿠키 사용 시
		// String accessToken = response.accessToken();
		// String refreshToken = response.refreshToken();
		// HttpHeaders tokenHeaders = cookieUtil.generateTokenCookies(accessToken, refreshToken);

		return ResponseEntity.ok()
			// .headers(tokenHeaders)
			.body(response);
	}
}
