package com.green.energy.tracker.cloud.statistics_service.service;

import com.green.energy.tracker.cloud.statistics_service.model.EventType;
import com.green.energy.tracker.cloud.statistics_service.model.GlobalStatistics;
import io.cloudevents.CloudEvent;

import java.util.concurrent.ExecutionException;

public interface GlobalStatisticsService {
    /**
     * Methods to handle global statistics based on events.
     */
    GlobalStatistics createGlobalStatisticsFromEvent() throws ExecutionException, InterruptedException;
    GlobalStatistics updateGlobalStatisticsFromEvent(CloudEvent cloudEvent, String source) throws ExecutionException, InterruptedException;
    boolean deleteGlobalStatisticsFromEvent() throws ExecutionException, InterruptedException;
    boolean exists() throws ExecutionException, InterruptedException;
}
