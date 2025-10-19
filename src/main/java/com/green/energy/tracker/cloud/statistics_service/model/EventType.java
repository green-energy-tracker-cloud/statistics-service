package com.green.energy.tracker.cloud.statistics_service.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum EventType {
    SENSOR_ADDED("google.cloud.datastore.entity.v1.created_sensor"),
    SENSOR_REMOVED("google.cloud.datastore.entity.v1.deleted_sensor"),
    SENSOR_UPDATED("google.cloud.datastore.entity.v1.updated_sensor"),
    SITE_ADDED("google.cloud.datastore.entity.v1.created_site"),
    SITE_REMOVED("google.cloud.datastore.entity.v1.deleted_site"),
    SITE_UPDATED("google.cloud.datastore.entity.v1.updated_site");

    private final String eventTypeDetail;

    public static EventType fromEventTypeDetail(String eventTypeDetail) {
        return Arrays.stream(EventType.values())
                .filter(eventType -> eventType.getEventTypeDetail().equalsIgnoreCase(eventTypeDetail))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown event type detail: " + eventTypeDetail));
    }

}
