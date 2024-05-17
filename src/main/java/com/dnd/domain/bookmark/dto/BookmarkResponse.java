package com.dnd.domain.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BookmarkResponse {
    private Long bookmarkId;
    private Long eventId;
    private String title;
    private String description;
    private String host;
    private Integer cost;
    private LocalDateTime startAt;
    private LocalDateTime finishAt;
    private String reservationUrl;
}
