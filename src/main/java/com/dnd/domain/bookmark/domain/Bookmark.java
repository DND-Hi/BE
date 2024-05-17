package com.dnd.domain.bookmark.domain;

import com.dnd.domain.common.model.BaseTimeEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    private Long memberId;

    private Long eventId;

    @Builder(access = AccessLevel.PRIVATE)
    private Bookmark(
        Long memberId,
        Long eventId) {
        this.memberId = memberId;
        this.eventId = eventId;
    }

    public static Bookmark createBookMark(Long memberId, Long eventId) {
        return Bookmark.builder()
                .memberId(memberId)
                .eventId(eventId)
                .build();
    }
}
