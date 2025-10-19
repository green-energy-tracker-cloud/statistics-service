package com.green.energy.tracker.cloud.statistics_service.utils;

import com.google.events.cloud.datastore.v1.EntityEventData;
import com.google.protobuf.InvalidProtocolBufferException;
import com.green.energy.tracker.cloud.statistics_service.model.EventType;
import com.green.energy.tracker.cloud.statistics_service.model.GlobalStatistics;
import io.cloudevents.CloudEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Objects;

@Slf4j
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

    public static GlobalStatistics updateGlobalStatistics(GlobalStatistics currentStats, CloudEvent cloudEvent, String source) throws InvalidProtocolBufferException {
        var eventType = EventType.fromEventTypeDetail(cloudEvent.getType() + "_" + source);
        var entityEventData = EntityEventData.parseFrom(Objects.requireNonNull(cloudEvent.getData()).toBytes());
        switch (eventType){
            case SITE_ADDED -> currentStats.setTotalSites(currentStats.getTotalSites()+1);
            case SITE_REMOVED -> currentStats.setTotalSites(Math.max(0,currentStats.getTotalSites()-1));
            case SITE_UPDATED -> currentStats.setTotalSites(currentStats.getTotalSites());
            case SENSOR_ADDED -> currentStats.setTotalSensors(currentStats.getTotalSensors()+1);
            case SENSOR_REMOVED -> currentStats.setTotalSensors(Math.max(0,currentStats.getTotalSensors()-1));
            case SENSOR_UPDATED -> {
                var oldEntityValues = entityEventData.getOldValue().getEntity().getPropertiesMap();
                log.info("Old Entity Values: {}", oldEntityValues);
                var newEntityValues = entityEventData.getValue().getEntity().getPropertiesMap();
                log.info("New Entity Values: {}", newEntityValues);
                var oldStatus = oldEntityValues.get("status").getStringValue();
                log.info("Old Status: {}", oldStatus);
                var newStatus = newEntityValues.get("status").getStringValue();
                log.info("New Status: {}", newStatus);
                var oldLastValue = oldEntityValues.get("lastValue").getDoubleValue();
                log.info("Old Last Value: {}", oldLastValue);
                var newLastValue = newEntityValues.get("lastValue").getDoubleValue();
                log.info("New Last Value: {}", newLastValue);
                if (!oldStatus.equals(newStatus)){
                    if (newStatus.equals("ACTIVE"))
                        currentStats.setActiveSensors(currentStats.getActiveSensors() + 1);
                    else if (oldStatus.equals("ACTIVE"))
                        currentStats.setActiveSensors(Math.max(0, currentStats.getActiveSensors() - 1));
                }
                if(oldLastValue != newLastValue){
                    var globalAverageValue = currentStats.getGlobalAverageValue() + (newLastValue - oldLastValue) / currentStats.getTotalSensors();
                    currentStats.setGlobalAverageValue(globalAverageValue);
                }
            }
            default -> throw new IllegalArgumentException("Unsupported event type for global statistics update: " + eventType);
        }
        currentStats.setGlobalStatisticsId(currentStats.getGlobalStatisticsId());
        currentStats.setAverageBySensorType(new HashMap<>());
        currentStats.setSensorsByStatus(new HashMap<>());
        return currentStats;
    }

}
