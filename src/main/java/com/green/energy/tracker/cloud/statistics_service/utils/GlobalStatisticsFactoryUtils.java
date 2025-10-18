package com.green.energy.tracker.cloud.statistics_service.utils;

import com.green.energy.tracker.cloud.statistics_service.model.GlobalStatistics;

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

    public static GlobalStatistics updateGlobalStatistics(GlobalStatistics currentStats){
        currentStats.setGlobalStatisticsId(currentStats.getGlobalStatisticsId());
        currentStats.setTotalSites(0);
        currentStats.setTotalSensors(0);
        currentStats.setActiveSensors(0);
        currentStats.setGlobalAverageValue(0);
        currentStats.setAverageBySensorType(new HashMap<>());
        currentStats.setSensorsByStatus(new HashMap<>());
        return currentStats;
    }

}
