package com.dnd.domain.image.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageType {
    EVENT("event"),
    MEMBER_PROFILE("member_profile"),
    ;
    private final String value;
}
