package com.green.energy.tracker.cloud.statistics_service.repository;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreFactory;
import com.google.cloud.firestore.FirestoreRpcFactory;
import com.green.energy.tracker.cloud.statistics_service.model.GlobalStatistics;
import com.green.energy.tracker.cloud.statistics_service.model.SiteStatistics;
import io.cloudevents.CloudEvent;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface GlobalStatisticsRepository {
    GlobalStatistics save() throws ExecutionException, InterruptedException;
    GlobalStatistics update(CloudEvent cloudEvent) throws ExecutionException, InterruptedException;
    boolean delete() throws ExecutionException, InterruptedException;
    boolean exists() throws ExecutionException, InterruptedException;
}
