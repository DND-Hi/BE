package com.dnd.domain.event.dao;

import com.dnd.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(nativeQuery = true, value =
            "SELECT * FROM event e "
                    + "WHERE ST_DISTANCE_SPHERE(point(:longitude, :latitude), e.location) < :distance")
    List<Event> findAllByLocation(@Param("longitude") Double longitude,
                                  @Param("latitude") Double latitude,
                                  @Param("distance") int distance);
}
