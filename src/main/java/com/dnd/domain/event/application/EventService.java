package com.dnd.domain.event.application;

import com.dnd.domain.bookmark.dao.BookmarkRepository;
import com.dnd.domain.bookmark.domain.Bookmark;
import com.dnd.domain.event.dao.EventLocationRepository;
import com.dnd.domain.event.domain.Event;
import com.dnd.domain.event.dao.EventRepository;
import com.dnd.domain.event.domain.HostType;
import com.dnd.domain.event.dto.*;
import com.dnd.domain.image.dao.ImageRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;
    private final BookmarkRepository bookmarkRepository;
    private final EventLocationRepository eventLocationRepository;
    private final ImageRepository imageRepository;
    private final StorageProperties storageProperties;
    private final SpringEnvironmentUtil springEnvironmentUtil;

    @Transactional
    public Long register(CreateEventRequest request, Long memberId) {
        Member member = memberRepository
            .findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        HostType hostType = HostType.PRIVATE;

        Event event = Event.createEvent(
                    request.getTitle(),
                    request.getDescription(),
                    request.getHost(),
                    hostType,
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
    public List<SearchEventResponse> searchEvents(Double latitude, Double longitude, int distance, Long memberId) {
        List<Long> result = eventLocationRepository.findAllByLocation(
                latitude, longitude, distance
        );

        List<Event> events = result.stream()
                .map(id -> eventRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND)))
                .toList();

        Set<Long> bookmarks = bookmarkRepository.findBookmarkByMemberId(memberId)
                .stream()
                .map(Bookmark::getEventId)
                .collect(Collectors.toSet());

        List<SearchEventResponse> response = events.stream()
                .map(event -> new SearchEventResponse(
                                event.getId(),
                                event.getTitle(),
                                event.getDescription(),
                                event.getHost(),
                                event.getImageUrl(),
                                event.getLongitude(),
                                event.getLatitude(),
                                event.getStartAt(),
                                event.getFinishAt(),
                                memberId != null && bookmarks.contains(event.getId())
                        )
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

    public List<MyEventResponse> myEvents(Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        List<Event> events = eventRepository.findAllByMember(member);
        List<MyEventResponse> response = events.stream()
                .map(event -> new MyEventResponse(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getHost(),
                        event.getImageUrl(),
                        event.getLongitude(),
                        event.getLatitude(),
                        event.getStartAt(),
                        event.getFinishAt())
                ).toList();
        return response;

    }

    public SearchEventResponse findEvent(Long eventId, Long memberId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));

        Set<Long> bookmarks = bookmarkRepository.findBookmarkByMemberId(memberId)
                .stream()
                .map(Bookmark::getEventId)
                .collect(Collectors.toSet());

        return new SearchEventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getHost(),
                event.getImageUrl(),
                event.getLongitude(),
                event.getLatitude(),
                event.getStartAt(),
                event.getFinishAt(),
                memberId != null && bookmarks.contains(event.getId())
        );
    }

    public List<SearchEventResponse> searchEvent(String keyword, Long memberId) {
        List<Event> events = eventRepository.findByTitleContaining(keyword);

        Set<Long> bookmarks = bookmarkRepository.findBookmarkByMemberId(memberId)
                .stream()
                .map(Bookmark::getEventId)
                .collect(Collectors.toSet());

        List<SearchEventResponse> response = events.stream()
                .map(event -> new SearchEventResponse(
                                event.getId(),
                                event.getTitle(),
                                event.getDescription(),
                                event.getHost(),
                                event.getImageUrl(),
                                event.getLongitude(),
                                event.getLatitude(),
                                event.getStartAt(),
                                event.getFinishAt(),
                        memberId != null && bookmarks.contains(event.getId())
                        )
                ).toList();
        return response;
    }

    public Long updateEvent(Long eventId, CreateEventRequest request, Long memberId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!event.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.EVENT_USER_MISMATCH);
        }

        event.updateEvent(
                request.getTitle(),
                request.getDescription(),
                request.getHost(),
                request.getLongitude(),
                request.getLatitude(),
                request.getStartAt(),
                request.getFinishAt(),
                request.getReservationUrl(),
                request.getCost(),
                request.getImageUrl()
        );

        return event.getId();
    }

    public void deleteEvent(Long eventId, Long memberId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!event.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.EVENT_USER_MISMATCH);
        }

        eventRepository.delete(event);
    }
}
