package com.green.energy.tracker.cloud.statistics_service.utils;

import com.google.events.cloud.firestore.v1.DocumentEventData;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.cloudevents.CloudEvent;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
public class CloudEventUtils {

    public static DocumentEventData getDocumentEventDataFromCloudEvent(CloudEvent cloudEvent) throws InvalidProtocolBufferException {
        var documentEventData = DocumentEventData.parseFrom(Objects.requireNonNull(cloudEvent.getData()).toBytes());
        log.info("Parsed DocumentEventData from CloudEvent: {}", documentEventData);
        return documentEventData;
    }
}
