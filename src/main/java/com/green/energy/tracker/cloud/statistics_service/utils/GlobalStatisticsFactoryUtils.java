package com.green.energy.tracker.cloud.statistics_service.utils;

import com.green.energy.tracker.cloud.statistics_service.model.EventType;
import com.green.energy.tracker.cloud.statistics_service.model.GlobalStatistics;
import io.cloudevents.CloudEvent;
import java.util.HashMap;

public class GlobalStatisticsFactoryUtils {

    public static GlobalStatistics createGlobalStatistics(String globalStatisticsId){
        return GlobalStatistics.builder()
                .globalStatisticsId(globalStatisticsId)
                .totalSites(0)
                .totalSensors(0)
                .activeSensors(0)
                .globalAverageValue(0.0)
                .averageBySensorType(new HashMap<>())
                .sensorsByStatus(new HashMap<>())
                .build();
    }

    public static GlobalStatistics updateGlobalStatistics(GlobalStatistics currentStats, CloudEvent cloudEvent, String source){
        var eventType = EventType.fromEventTypeDetail(cloudEvent.getType() + "_" + source);
        switch (eventType){
            case SITE_ADDED -> currentStats.setTotalSites(currentStats.getTotalSites()+1);
            case SITE_REMOVED -> currentStats.setTotalSites(Math.max(0,currentStats.getTotalSites()-1));
            case SITE_UPDATED -> currentStats.setTotalSites(currentStats.getTotalSites());
            case SENSOR_ADDED -> currentStats.setTotalSensors(currentStats.getTotalSensors()+1);
            case SENSOR_REMOVED -> currentStats.setTotalSensors(Math.max(0,currentStats.getTotalSensors()-1));
            case SENSOR_UPDATED -> {
                // No direct changes to global statistics for updates in this simplified model
            }
            default -> throw new IllegalArgumentException("Unsupported event type for global statistics update: " + eventType);
        }
        currentStats.setGlobalStatisticsId(currentStats.getGlobalStatisticsId());
        currentStats.setActiveSensors(0);
        currentStats.setGlobalAverageValue(0);
        currentStats.setAverageBySensorType(new HashMap<>());
        currentStats.setSensorsByStatus(new HashMap<>());
        return currentStats;
    }

}
