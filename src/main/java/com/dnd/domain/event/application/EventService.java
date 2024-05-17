package com.dnd.domain.event.application;

import com.dnd.domain.event.dao.EventLocationRepository;
import com.dnd.domain.event.domain.Event;
import com.dnd.domain.event.dao.EventRepository;
import com.dnd.domain.event.domain.EventLocation;
import com.dnd.domain.event.dto.CreateEventRequest;
import com.dnd.domain.event.dto.SearchEventProjection;
import com.dnd.domain.event.dto.SearchEventRequest;
import com.dnd.domain.event.dto.SearchEventResponse;
import com.dnd.domain.image.dao.ImageRepository;
import com.dnd.domain.image.domain.Image;
import com.dnd.domain.image.domain.ImageFileExtension;
import com.dnd.domain.image.domain.ImageType;
import com.dnd.domain.member.dao.MemberRepository;
import com.dnd.domain.member.domain.Member;
import com.dnd.global.error.exception.CustomException;
import com.dnd.global.error.exception.ErrorCode;
import com.dnd.global.util.SpringEnvironmentUtil;
import com.dnd.infra.storage.StorageProperties;

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
    private final MemberRepository memberRepository;
    private final EventLocationRepository eventLocationRepository;
    private final ImageRepository imageRepository;
    private final StorageProperties storageProperties;
    private final SpringEnvironmentUtil springEnvironmentUtil;

    @Transactional
    public Long register(CreateEventRequest request, Long memberId) {
        Member member = memberRepository
            .findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Event event = Event.createEvent(
                    request.getTitle(),
                    request.getDescription(),
                    request.getHost(),
                    request.getLongitude(),
                    request.getLatitude(),
                    request.getStartAt(),
                    request.getFinishAt(),
                    request.getReservationUrl(),
                    request.getCost(),
                    request.getImageUrl(),
                    member
            );

        eventRepository.save(event);
        eventLocationRepository.saveWithPoint(event.getId(), makePoint(request.getLatitude(), request.getLongitude()));

        return event.getId();
    }

    @Transactional(readOnly = true)
    public List<SearchEventResponse> searchEvents(SearchEventRequest request) {
        List<Long> result = eventLocationRepository.findAllByLocation(
                request.getLongitude(),
                request.getLatitude(),
                request.getDistance()
        );

        List<Event> events = result.stream()
                .map(id -> eventRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND)))
                .toList();

        List<SearchEventResponse> response = events.stream()
                .map(event -> new SearchEventResponse(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getHost(),
                        event.getLongitude(),
                        event.getLatitude(),
                        event.getStartAt(),
                        event.getFinishAt())
                ).toList();

        return response;
    }

    private String makePoint(Double latitude, Double longitude) {
        return String.format("POINT(%s %s)", latitude, longitude);
    }

    private Point getGeoInfo(Double longitude, Double latitude) throws ParseException {

        String wkt = String.format("POINT(%s %s)", latitude, longitude);
        return (Point)new WKTReader().read(wkt);
    }
}
