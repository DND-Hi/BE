package com.dnd.domain.event.dao;

import com.dnd.domain.event.domain.EventLocation;
import com.dnd.domain.event.dto.SearchEventProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventLocationRepository extends JpaRepository<EventLocation, Long> {

    @Modifying
    @Query(value = "INSERT INTO event_location (event_id, location) " +
            "VALUES (:eventId, ST_PointFromText(:point))", nativeQuery = true)
    void saveWithPoint(
            @Param("eventId") Long eventId,
            @Param("point") String point
    );

    @Query(nativeQuery = true, value =
            "SELECT event_id FROM event_location e WHERE ST_DISTANCE_SPHERE(point(:longitude, :latitude), e.location) < :distance")
    List<Long> findAllByLocation(@Param("longitude") Double longitude,
                                                  @Param("latitude") Double latitude,
                                                  @Param("distance") int distance);

}
