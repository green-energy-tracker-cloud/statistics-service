package com.green.energy.tracker.cloud.statistics_service.repository;

import com.google.cloud.firestore.Firestore;
import com.google.protobuf.InvalidProtocolBufferException;
import com.green.energy.tracker.cloud.statistics_service.model.GlobalStatistics;
import com.green.energy.tracker.cloud.statistics_service.utils.GlobalStatisticsFactoryUtils;
import io.cloudevents.CloudEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GlobalStatisticsRepositoryFirestoreImpl implements GlobalStatisticsRepository {

    private final Firestore firestoreClient;
    public static final String GLOBAL_STATISTICS_COLLECTION = "global_statistics";


    @Override
    public GlobalStatistics save() throws ExecutionException, InterruptedException {
        log.debug("Saving new global statistics");
        var globalStatisticsId = UUID.randomUUID().toString();
        var globalStatistics = GlobalStatisticsFactoryUtils.createGlobalStatistics(globalStatisticsId);
        firestoreClient.collection(GLOBAL_STATISTICS_COLLECTION)
                .document(globalStatisticsId)
                .set(globalStatistics)
                .get();
        log.info("Global statistics saved successfully: {}", globalStatistics);
        return globalStatistics;
    }

    @Override
    public GlobalStatistics update(CloudEvent cloudEvent, String source) throws ExecutionException, InterruptedException, InvalidProtocolBufferException {
        var documentsToUpdate = GlobalStatisticsFactoryUtils.updateGlobalStatistics(getGlobalStatisticsFromDocuments(),cloudEvent,source);
        firestoreClient.collection(GLOBAL_STATISTICS_COLLECTION)
                .document(documentsToUpdate.getGlobalStatisticsId())
                .set(documentsToUpdate)
                .get();
        return documentsToUpdate;
    }

    @Override
    public boolean exists() throws ExecutionException, InterruptedException {
        return !firestoreClient.collection(GLOBAL_STATISTICS_COLLECTION).get().get().getDocuments().isEmpty();
    }

    private GlobalStatistics getGlobalStatisticsFromDocuments() throws ExecutionException, InterruptedException {
        var documents = firestoreClient.collection(GLOBAL_STATISTICS_COLLECTION).get().get().getDocuments();
        return documents.get(0).toObject(GlobalStatistics.class);
    }

}
