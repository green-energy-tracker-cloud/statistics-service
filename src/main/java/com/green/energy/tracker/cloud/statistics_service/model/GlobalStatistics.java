package com.green.energy.tracker.cloud.statistics_service.model;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 * Model representing global aggregated statistics across all sites and sensors.
 */
public class GlobalStatistics {
    private long totalSites;
    private long totalSensors;
    private long activeSensors;
    private double globalAverageValue;
    private Map<String, Double> averageBySensorType;
    private Map<String, Long> sensorsByStatus;

    @AllArgsConstructor
    @Getter
    enum Fields {
        TOTAL_SITES("total_sites"),
        TOTAL_SENSORS("total_sites"),
        ACTIVE_SENSORS("total_sites"),
        GLOBAL_AVERAGE_VALUE("total_sites"),
        AVERAGE_BY_SENSOR_TYPE("total_sites"),
        SENSORS_BY_STATUS("total_sites");
        final String fieldName;
    }
}
