package com.green.energy.tracker.cloud.statistics_service.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 * Model representing aggregated statistics for a site.
 */
public class SiteStatistics {
    private String siteId;
    private String siteName;
    private int totalSensors;
    private int activeSensors;
    private double averageLastValue;
    private double maxLastValue;
    private double minLastValue;
    private Map<String, Double> averageBySensorType;
    private Instant lastUpdated;
}
