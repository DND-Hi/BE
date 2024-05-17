package com.dnd.domain.event.domain;

import com.dnd.domain.common.model.BaseTimeEntity;
import com.dnd.domain.member.domain.Member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    private String title;

    private String description;

    private String host;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ImageUploadStatus uploadStatus;

    @Column(columnDefinition = "POINT SRID 4326")
    private Point location;

    private Double longitude;

    private Double latitude;

    private LocalDateTime startAt;

    private LocalDateTime finishAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String reservationUrl;

    private Integer cost;

    @Builder(access = AccessLevel.PRIVATE)
    public Event(String title,
                 String description,
                 String host,
                 Point location,
                 Double longitude,
                 Double latitude,
                String imageUrl,
                 LocalDateTime startAt,
                 LocalDateTime finishAt,
                 String reservationUrl,
                 Integer cost,
                 Member member) {
        this.title = title;
        this.description = description;
        this.host = host;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.imageUrl = imageUrl;
        this.startAt = startAt;
        this.finishAt = finishAt;
        this.member = member;
        this.reservationUrl = reservationUrl;
        this.cost = cost;
    }

    public static Event createEvent(
            String title,
            String description,
            String host,
            Point location,
            Double longitude,
            Double latitude,
            LocalDateTime startAt,
            LocalDateTime finishAt,
            String reservationUrl,
            Integer cost,
            Member member) {

        return Event.builder()
                .title(title)
                .description(description)
                .host(host)
                .location(location)
                .longitude(longitude)
                .latitude(latitude)
                .startAt(startAt)
                .finishAt(finishAt)
                .member(member)
                .reservationUrl(reservationUrl)
                .cost(cost)
                .build();
    }
}
