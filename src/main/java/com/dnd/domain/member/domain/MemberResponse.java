package com.dnd.domain.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private String profileImage;
    private String email;
    private String nickname;

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getProfileImage(),
                member.getOauthInfo().getOauthEmail(),
                member.getNickname()
        );
    }
}
