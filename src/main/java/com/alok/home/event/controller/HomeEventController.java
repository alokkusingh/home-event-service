package com.alok.home.event.controller;

import com.alok.home.event.dto.EventDto;
import com.alok.home.event.service.HomeEventService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class HomeEventController {

    final private HomeEventService homeEventService;

    public HomeEventController(HomeEventService homeEventService) {
        this.homeEventService = homeEventService;
    }

    @GetMapping(value = "/{eventId}/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeForEvent(@PathVariable String eventId) {

        return homeEventService.subscribeForEvent(eventId);
    }

    @PostMapping(value = "/{eventId}/emit")
    public ResponseEntity<Void> emitEvent(
            @PathVariable String eventId,
            @RequestBody EventDto eventDto
    ) {

        homeEventService.emitEvent(eventId, eventDto);

        return ResponseEntity.accepted().build();
    }
}
