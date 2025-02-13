package com.alok.home.event.service;

import com.alok.home.event.dto.EventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class HomeEventService {

    private final Map<String, SseEmitter> eventSubscriptionMap = new ConcurrentHashMap<>();
    private final long TIMEOUT = 300000L;

    public SseEmitter subscribeForEvent(String eventId) {
        log.info("Subscribing for event: {}", eventId);
        SseEmitter sseEmitter = new SseEmitter(TIMEOUT);
        sseEmitter.onTimeout(
                () -> {
                    log.warn("Timed-out for subscription: {}", eventId);
                    eventSubscriptionMap.remove(eventId);
                }
        );

        sseEmitter.onCompletion(() -> {
            log.info("Subscription completed: {}", eventId);
            eventSubscriptionMap.remove(eventId);
        });

        eventSubscriptionMap.put(eventId, sseEmitter);

        return sseEmitter;
    }

    public void emitEvent(String eventId, EventDto eventDto) {
        log.info("Emitting event: {}", eventId);
        SseEmitter sseEmitter = eventSubscriptionMap.get(eventId);

        if (sseEmitter != null) {
            try {
                sseEmitter.send(SseEmitter.event()
                        .id(eventId)
                        .data(eventDto)
                        .name(eventDto.getEventType().name())
                        .build());
            } catch (Exception e) {
                eventSubscriptionMap.remove(eventId);
            }
        }
    }
}
