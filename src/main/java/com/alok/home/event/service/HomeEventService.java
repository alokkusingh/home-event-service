package com.alok.home.event.service;

import com.alok.home.event.dto.EventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class HomeEventService {

    private final Map<String, SseEmitter> eventSubscriptionMap = new ConcurrentHashMap<>();
    private final long TIMEOUT = 300000L;

    public SseEmitter subscribeForEvent(String eventId) {
        log.info("Subscribing for event: {}", eventId);
        //SseEmitter sseEmitter = eventSubscriptionMap.get(eventId);

        SseEmitter sseEmitter = new SseEmitter(TIMEOUT);
        sseEmitter.onTimeout(
                () -> {
                    log.warn("Timed-out for subscription: {}", eventId);
                    //eventSubscriptionMap.remove(eventId);
                }
        );

        sseEmitter.onCompletion(() -> {
            log.info("Subscription completed: {}", eventId);
            //eventSubscriptionMap.remove(eventId);
        });

        SseEmitter oldSubscription = eventSubscriptionMap.put(eventId, sseEmitter);
//        if (oldSubscription != null) {
//            log.info("Event subscription already exists, completing the old event: {}", oldSubscription);
//            try {
//                oldSubscription.send(SseEmitter.event().comment("Subscription replaced").build());
//                oldSubscription.complete();
//            } catch (Exception e) {
//                log.error("Error while sending event to old subscription: {}", oldSubscription, e);
//            }
//        }

        return sseEmitter;
    }

    public void emitEvent(String eventId, EventDto eventDto) {
        log.info("Emitting event: {}", eventId);
        SseEmitter sseEmitter = eventSubscriptionMap.get(eventId);

        if (sseEmitter != null) {
            try {
                sseEmitter.send(SseEmitter.event()
                        .id(eventId)
                        .data(eventDto, MediaType.APPLICATION_JSON)
                        .name(eventDto.getEventType().name())
                        .reconnectTime(30000)
                        .build());
            } catch (Exception e) {
                eventSubscriptionMap.remove(eventId);
            }
        }
    }
}
