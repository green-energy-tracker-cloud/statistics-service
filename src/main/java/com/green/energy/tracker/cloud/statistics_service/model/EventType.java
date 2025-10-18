package com.green.energy.tracker.cloud.statistics_service.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum EventType {
    SENSOR_ADDED("google.cloud.datastore.entity.v1.created"),
    SENSOR_REMOVED("google.cloud.datastore.entity.v1.deleted"),
    SENSOR_UPDATED("google.cloud.datastore.entity.v1.updated"),
    SITE_ADDED("google.cloud.datastore.entity.v1.create"),
    SITE_REMOVED("google.cloud.datastore.entity.v1.deleted"),
    SITE_UPDATED("google.cloud.datastore.entity.v1.updated");

    private final String eventTypeDetail;

    public static EventType fromEventTypeDetail(String eventTypeDetail) {
        return Arrays.stream(EventType.values())
                .filter(eventType -> eventType.getEventTypeDetail().equalsIgnoreCase(eventTypeDetail))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown event type detail: " + eventTypeDetail));
    }

}
