package com.dnd.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthUserInfo {
    private final Long userId;
    private final String role;

    public static AuthUserInfo from(Long userId) {
        return AuthUserInfo.builder()
                .userId(userId)
                .role("user")  //임의로 설정
                .build();
    }
}
