package com.dnd.domain.event.api;

import com.dnd.domain.common.annotation.LoginUsers;
import com.dnd.domain.event.application.EventService;
import com.dnd.domain.event.dto.CreateEventRequest;
import com.dnd.domain.event.dto.SearchEventRequest;
import com.dnd.domain.event.dto.SearchEventResponse;
import com.dnd.global.common.response.GlobalResponse;
import com.dnd.global.config.security.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
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
    public GlobalResponse create(
        @RequestBody @Valid CreateEventRequest request,
        @LoginUsers CustomUserDetails userDetails) {
        Long eventId = eventService.register(request, userDetails.getMemberId());

        return GlobalResponse.success(200, eventId);
    }

    @Operation(summary = "이벤트 조회")
    @GetMapping
    public GlobalResponse search(@RequestBody @Valid SearchEventRequest request) {
        List<SearchEventResponse> searchResults = eventService.searchEvents(request);
        return GlobalResponse.success(200, searchResults);
    }
}
