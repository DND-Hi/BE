package com.dnd.domain.bookmark.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateBookmarkRequest {
    @NotNull
    private Long eventId;
}
