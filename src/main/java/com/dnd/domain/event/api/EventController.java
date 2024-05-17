package com.dnd.domain.event.api;

import com.dnd.domain.event.application.EventService;
import com.dnd.domain.event.dto.CreateEventRequest;
import com.dnd.domain.event.dto.SearchEventProjection;
import com.dnd.domain.event.dto.SearchEventRequest;
import com.dnd.global.common.response.GlobalResponse;
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
    @PostMapping("/create")
    public GlobalResponse create(@RequestBody @Valid CreateEventRequest request) {
        Long eventId = eventService.register(request);

        return GlobalResponse.success(200, eventId);
    }

    @Operation(summary = "이벤트 조회")
    @GetMapping("/search")
    public GlobalResponse search(@RequestBody @Valid SearchEventRequest request) {
        List<SearchEventProjection> searchResults = eventService.searchEvents(request);
        return GlobalResponse.success(200, searchResults);
    }
}
