package com.dnd.domain.auth.service;

import com.dnd.domain.auth.dto.AuthUserInfo;
import com.dnd.domain.auth.dto.JwtAuthentication;
import com.dnd.domain.auth.dto.JwtAuthenticationToken;
import com.dnd.domain.auth.dto.Tokens;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;

    public Tokens createTokens(AuthUserInfo authUserInfo) {
        Long userId = authUserInfo.getUserId();
        String role = authUserInfo.getRole();

        String accessToken = createAccessToken(userId, role);

        log.info("[Token] = {}", accessToken);
        return new Tokens(accessToken);
    }

    private String createAccessToken(Long userId, String role) {
        return jwtTokenProvider.getAccessToken(userId, role);
    }

    public JwtAuthenticationToken getAuthenticationByAccessToken(String accessToken) {
        jwtTokenProvider.validateToken(accessToken);

        Claims claims = jwtTokenProvider.getClaims(accessToken);

        Long id = claims.get("userId", Long.class);
        String role = claims.get("role", String.class);

        JwtAuthentication principal = new JwtAuthentication(id, accessToken);
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        return new JwtAuthenticationToken(principal, null, authorities);
    }

}
