package com.dnd.domain.event.application;

import com.dnd.domain.event.Event;
import com.dnd.domain.event.dao.EventRepository;
import com.dnd.domain.event.dto.CreateEventRequest;
import com.dnd.domain.event.dto.SearchEventProjection;
import com.dnd.domain.event.dto.SearchEventRequest;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    @Transactional
    public Long register(CreateEventRequest request) {
        Event event;

        try {
            event = Event.createEvent(
                    request.getTitle(),
                    request.getDescription(),
                    request.getHost(),
                    getGeoInfo(request.getLongitude(), request.getLatitude()),
                    request.getLongitude(),
                    request.getLatitude(),
                    request.getStartAt(),
                    request.getFinishAt()
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        eventRepository.saveWithPoint(
                request.getTitle(),
                request.getDescription(),
                request.getHost(),
                makePoint(request.getLatitude(), request.getLongitude()),
                request.getLongitude(),
                request.getLatitude(),
                request.getStartAt(),
                request.getFinishAt()
        );

        return event.getId();
    }

    @Transactional(readOnly = true)
    public List<SearchEventProjection> searchEvents(SearchEventRequest request) {
        return eventRepository.findAllByLocation(
                request.getLongitude(),
                request.getLatitude(),
                request.getDistance()
        );
    }

    private String makePoint(Double latitude, Double longitude) {
        return String.format("POINT(%s %s)", latitude, longitude);
    }

    private Point getGeoInfo(Double longitude, Double latitude) throws ParseException {

        String wkt = String.format("POINT(%s %s)", latitude, longitude);
        return (Point)new WKTReader().read(wkt);
    }
}
