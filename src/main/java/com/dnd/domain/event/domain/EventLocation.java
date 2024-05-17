package com.dnd.domain.event.domain;

import com.dnd.domain.member.domain.Member;
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
public class EventLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_location_id")
    private Long id;

    @Column(name = "event_id")
    private Long eventId;

    @Column(columnDefinition = "POINT SRID 4326")
    private Point location;

    @Builder(access = AccessLevel.PRIVATE)
    public EventLocation(
            Long eventId,
            Point location) {
        this.eventId = eventId;
        this.location = location;
    }

    public static EventLocation createEventLocation(Long eventId, Point location) {
        return EventLocation.builder()
                .eventId(eventId)
                .location(location)
                .build();
    }
}
