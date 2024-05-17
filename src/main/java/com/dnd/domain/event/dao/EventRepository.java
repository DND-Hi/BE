package com.dnd.domain.event.dao;

import com.dnd.domain.event.dto.SearchEventProjection;
import com.dnd.domain.event.domain.Event;
import com.dnd.domain.member.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(nativeQuery = true, value =
            "SELECT event_id, title, description, host, longitude, latitude, start_at, finish_at, reservation_url, cost FROM event e "
                    + "WHERE ST_DISTANCE_SPHERE(point(:longitude, :latitude), e.location) < :distance")
    List<SearchEventProjection> findAllByLocation(@Param("longitude") Double longitude,
                                                  @Param("latitude") Double latitude,
                                                  @Param("distance") int distance);

    @Modifying
    @Query(value = "INSERT INTO event (title, description, host, location, longitude, latitude, start_at, finish_at, reservation_url, cost) " +
            "VALUES (:title, :description, :host, ST_PointFromText(:point), :longitude, :latitude, :startAt, :finishAt, :reservationUrl, :cost)", nativeQuery = true)
    void saveWithPoint(
            @Param("title") String title,
            @Param("description") String description,
            @Param("host") String host,
            @Param("point") String point,
            @Param("longitude") Double longitude,
            @Param("latitude") Double latitude,
            @Param("startAt") LocalDateTime startAt,
            @Param("finishAt") LocalDateTime finishAt,
            @Param("reservationUrl") String reservationUrl,
            @Param("cost") Integer cost
    );

    List<Event> findAllByMember(Member member);
}
