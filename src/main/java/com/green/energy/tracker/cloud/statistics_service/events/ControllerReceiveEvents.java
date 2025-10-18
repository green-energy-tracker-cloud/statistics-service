package com.green.energy.tracker.cloud.statistics_service.events;

import com.google.protobuf.InvalidProtocolBufferException;
import com.green.energy.tracker.cloud.statistics_service.model.GlobalStatistics;
import com.green.energy.tracker.cloud.statistics_service.service.GlobalStatisticsService;
import io.cloudevents.spring.http.CloudEventHttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ControllerReceiveEvents{

    private final GlobalStatisticsService globalStatisticsService;

    @PostMapping("/sites-events")
    public ResponseEntity<GlobalStatistics> handleSitesFirestoreEvent(@RequestHeader HttpHeaders headers, @RequestBody byte[] event) throws ExecutionException, InterruptedException, InvalidProtocolBufferException {
        var cloudEvent = CloudEventHttpUtils.toReader(headers, ()-> event).toEvent();
        log.info("Received site event: {}", cloudEvent);
        return ResponseEntity.ok(globalStatisticsService.updateGlobalStatisticsFromEvent());
    }

    @PostMapping("/sensors-events")
    public ResponseEntity<GlobalStatistics> handleSensorsFirestoreEvent(@RequestBody byte[] event) throws ExecutionException, InterruptedException {
        log.info("Received sensor event: {}", event);
        return ResponseEntity.ok(globalStatisticsService.updateGlobalStatisticsFromEvent());
    }
}
