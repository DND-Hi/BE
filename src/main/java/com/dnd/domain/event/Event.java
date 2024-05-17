package com.dnd.domain.event;

import com.dnd.domain.common.model.BaseTimeEntity;
import com.dnd.domain.member.domain.Member;
import com.dnd.domain.member.domain.MemberRole;
import com.dnd.domain.member.domain.MemberStatus;
import com.dnd.domain.member.domain.OauthInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    private String title;

    private String description;

    @Column(nullable = false)
    private Point location;

    private LocalDateTime startAt;

    private LocalDateTime finishAt;

    @Builder(access = AccessLevel.PRIVATE)
    public Event(String title,
                 String description,
                 Point location,
                 LocalDateTime startAt,
                 LocalDateTime finishAt) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.startAt = startAt;
        this.finishAt = finishAt;
    }

    public static Event createEvent(
            String title,
            String description,
            Point location,
            LocalDateTime startAt,
            LocalDateTime finishAt) {

        return Event.builder()
                .title(title)
                .description(description)
                .location(location)
                .startAt(startAt)
                .finishAt(finishAt)
                .build();
    }
}