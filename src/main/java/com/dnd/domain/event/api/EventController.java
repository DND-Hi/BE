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
<<<<<<< Updated upstream
    @PostMapping
    public GlobalResponse create(
=======
    @PostMapping("/create")
    public Long create(
>>>>>>> Stashed changes
        @RequestBody @Valid CreateEventRequest request,
        @Parameter(hidden = true)
        @LoginUsers CustomUserDetails userDetails) {

		return eventService.register(request, userDetails.getMemberId());
    }

    @Operation(summary = "이벤트 조회")
<<<<<<< Updated upstream
    @GetMapping
    public GlobalResponse search(@RequestBody @Valid SearchEventRequest request) {
        List<SearchEventResponse> searchResults = eventService.searchEvents(request);
        return GlobalResponse.success(200, searchResults);
=======
    @GetMapping("/search")
    public List<SearchEventResponse>  search(@RequestBody @Valid SearchEventRequest request) {
        return  eventService.searchEvents(request);
>>>>>>> Stashed changes
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

}
