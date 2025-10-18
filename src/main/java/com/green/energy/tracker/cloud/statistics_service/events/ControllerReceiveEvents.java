package com.green.energy.tracker.cloud.statistics_service.events;

import com.green.energy.tracker.cloud.statistics_service.model.GlobalStatistics;
import com.green.energy.tracker.cloud.statistics_service.service.GlobalStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ControllerReceiveEvents {

    private final GlobalStatisticsService globalStatisticsService;

    @PostMapping("/sites-events")
    public ResponseEntity<GlobalStatistics> handleSitesFirestoreEvent(@RequestBody String cloudEventJson) throws ExecutionException, InterruptedException {
        log.info("Received site event: {}", cloudEventJson);
        return ResponseEntity.ok(globalStatisticsService.updateGlobalStatisticsFromEvent());
    }

    @PostMapping("/sensors-events")
    public ResponseEntity<GlobalStatistics> handleSensorsFirestoreEvent(@RequestBody String cloudEventJson) throws ExecutionException, InterruptedException {
        log.info("Received sensor event: {}", cloudEventJson);
        return ResponseEntity.ok(globalStatisticsService.updateGlobalStatisticsFromEvent());
    }
}
