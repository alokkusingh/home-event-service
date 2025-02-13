package com.alok.home.event.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class EventDto {
    private EventType eventType;
    private String eventData;
}
