package com.green.energy.tracker.cloud.statistics_service.service;

import com.green.energy.tracker.cloud.statistics_service.model.GlobalStatistics;
import com.green.energy.tracker.cloud.statistics_service.repository.GlobalStatisticsRepository;
import io.cloudevents.CloudEvent;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j

public class GlobalStatisticsServiceImpl implements GlobalStatisticsService {

    private final GlobalStatisticsRepository globalStatisticsRepository;

    @CircuitBreaker(name = "firestoreCb", fallbackMethod = "createGlobalStatisticsFromEventFallback")
    @Override
    public GlobalStatistics createGlobalStatisticsFromEvent() throws ExecutionException, InterruptedException {
        return globalStatisticsRepository.save();
    }

    @CircuitBreaker(name = "firestoreCb", fallbackMethod = "updateGlobalStatisticsFromEventFallback")
    @Override
    public GlobalStatistics updateGlobalStatisticsFromEvent(CloudEvent cloudEvent) throws ExecutionException, InterruptedException {
        return globalStatisticsRepository.update(cloudEvent);
    }

    @CircuitBreaker(name = "firestoreCb", fallbackMethod = "deleteGlobalStatisticsFromEventFallback")
    @Override
    public boolean deleteGlobalStatisticsFromEvent() throws ExecutionException, InterruptedException {
        return globalStatisticsRepository.delete();
    }

    @Override
    public boolean exists() throws ExecutionException, InterruptedException {
        return globalStatisticsRepository.exists();
    }

    public GlobalStatistics createGlobalStatisticsFromEventFallback(Throwable error) {
        log.error("Firestore is unavailable. Falling back to default behavior for createGlobalStatisticsFromEvent. Error: {}", error.getMessage());
        throw new RuntimeException("Firestore is unavailable. Please try again later.");
    }

    public GlobalStatistics updateGlobalStatisticsFromEventFallback(Throwable error) {
        log.error("Firestore is unavailable. Falling back to default behavior for updateGlobalStatisticsFromEvent. Error: {}", error.getMessage());
        throw new RuntimeException("Firestore is unavailable. Please try again later.");
    }

    public GlobalStatistics deleteGlobalStatisticsFromEventFallback(Throwable error) {
        log.error("Firestore is unavailable. Falling back to default behavior for deleteGlobalStatisticsFromEvent. Error: {}", error.getMessage());
        throw new RuntimeException("Firestore is unavailable. Please try again later.");
    }
}
