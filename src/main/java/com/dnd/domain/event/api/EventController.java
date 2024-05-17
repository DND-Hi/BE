package com.dnd.domain.event.api;

import com.dnd.domain.event.application.EventService;
import com.dnd.domain.event.dto.CreateEventRequest;
import com.dnd.domain.event.dto.SearchEventRequest;
import com.dnd.global.common.response.GlobalResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/event")
@RestController
public class EventController {

    private final EventService eventService;

    @PostMapping("/create")
    public GlobalResponse create(@RequestBody @Valid CreateEventRequest request) {
        Long eventId = eventService.register(request);

        return GlobalResponse.success(200, eventId);
    }

    @GetMapping("/search")
    public void search(@RequestBody @Valid SearchEventRequest request) {
        eventService.searchEvents(request);
    }
}
