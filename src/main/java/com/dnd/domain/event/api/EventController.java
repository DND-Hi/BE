package com.dnd.domain.event.api;

import com.dnd.domain.event.application.EventService;
import com.dnd.domain.event.dto.CreateEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/event")
@RestController
public class EventController {

    private final EventService eventService;

    @PostMapping("/create")
    public void create(CreateEventRequest createEventRequest) {
        eventService.register(createEventRequest);
    }
}
