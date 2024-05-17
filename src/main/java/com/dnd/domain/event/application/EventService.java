package com.dnd.domain.event.application;

import com.dnd.domain.event.Event;
import com.dnd.domain.event.dao.EventRepository;
import com.dnd.domain.event.dto.CreateEventRequest;
import com.dnd.domain.event.dto.SearchEventRequest;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Long register(CreateEventRequest request) {
        Event event = Event.createEvent(
                request.getTitle(),
                request.getDescription(),
                request.getHost(),
                getGeoInfo(request.getLongitude(), request.getLatitude()),
                request.getStartAt(),
                request.getFinishAt()
        );

        eventRepository.save(event);

        return event.getId();
    }

    public void searchEvents(SearchEventRequest request) {
        List<Event> events = eventRepository.findAllByLocation(
                getGeoInfo(request.getLongitude(), request.getLatitude()),
                request.getDistance()
        );

        return;
    }
    private Point getGeoInfo(Double longitude, Double latitude) {
        GeometryFactory gf = new GeometryFactory();

        return gf.createPoint(new Coordinate(
                longitude,
                latitude)
        );
    }
}
