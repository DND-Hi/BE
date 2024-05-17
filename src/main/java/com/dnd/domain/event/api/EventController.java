package com.dnd.domain.event.api;

import com.dnd.domain.common.annotation.LoginUsers;
import com.dnd.domain.event.application.EventService;
import com.dnd.domain.event.dto.CreateEventRequest;
import com.dnd.domain.event.dto.SearchEventRequest;
import com.dnd.domain.event.dto.SearchEventResponse;
import com.dnd.global.common.response.GlobalResponse;
import com.dnd.global.config.security.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/event")
@RestController
public class EventController {

    private final EventService eventService;

    @Operation(summary = "이벤트 생성")
    @PostMapping
    public Long create(
        @RequestBody @Valid CreateEventRequest request,
        @Parameter(hidden = true)
        @LoginUsers CustomUserDetails userDetails) {

		return eventService.register(request, userDetails.getMemberId());
    }

    @Operation(summary = "이벤트 조회")
    @GetMapping
    public List<SearchEventResponse> search(@RequestBody @Valid SearchEventRequest request) {
        return eventService.searchEvents(request);
    }

    @Operation(summary = "내가 만든 축제 조회")
    @GetMapping("/my")
    public List<SearchEventResponse> myEvents
        (@Parameter(hidden = true) @LoginUsers CustomUserDetails userDetails) {
        return eventService.myEvents(userDetails.getMemberId());
    }

    @Operation(summary = "이벤트 상세 조회")
    @GetMapping("/{eventId}")
    public SearchEventResponse eventFind(@PathVariable Long eventId) {
        return eventService.findEvent(eventId);
    }

    @Operation(summary = "이벤트 검색")
    @GetMapping("/search")
    public List<SearchEventResponse> eventSearch(@RequestParam String keyword) {
        return eventService.searchEvent(keyword);
    }
}
