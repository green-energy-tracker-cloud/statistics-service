package com.green.energy.tracker.cloud.statistics_service.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.green.energy.tracker.cloud.statistics_service.model.GlobalStatistics;
import io.cloudevents.CloudEvent;
import java.util.concurrent.ExecutionException;

public interface GlobalStatisticsService {
    /* Creates the GlobalStatistics document in Firestore based on the received event */
    GlobalStatistics createGlobalStatisticsFromEvent() throws ExecutionException, InterruptedException;
    /* Updates the GlobalStatistics document in Firestore based on the received event */
    GlobalStatistics updateGlobalStatisticsFromEvent(CloudEvent cloudEvent, String source) throws ExecutionException, InterruptedException, InvalidProtocolBufferException;
    /* Checks if the GlobalStatistics document exists in Firestore */
    boolean exists() throws ExecutionException, InterruptedException;
}
