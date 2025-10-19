package com.green.energy.tracker.cloud.statistics_service.repository;

import com.google.cloud.firestore.Firestore;
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
public class GlobalStatisticsRepositoryFirebaseImpl implements GlobalStatisticsRepository {

    private final Firestore firestoreClient;
    public static final String GLOBAL_STATISTICS_COLLECTION = "global_statistics";


    @Override
    public GlobalStatistics save() throws ExecutionException, InterruptedException {
        if(exists())
            return getGlobalStatisticsFromDocuments();
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
    public GlobalStatistics update(CloudEvent cloudEvent, String source) throws ExecutionException, InterruptedException {
        var documentsToUpdate = exists() ? GlobalStatisticsFactoryUtils.updateGlobalStatistics(getGlobalStatisticsFromDocuments(),cloudEvent,source) : save();
        firestoreClient.collection(GLOBAL_STATISTICS_COLLECTION)
                .document(documentsToUpdate.getGlobalStatisticsId())
                .set(documentsToUpdate)
                .get();
        return documentsToUpdate;
    }

    @Override
    public boolean delete() throws ExecutionException, InterruptedException {
        var deleteDocs = firestoreClient.collection(GLOBAL_STATISTICS_COLLECTION).document().delete().get();
        log.info("Global statistics deleted successfully. UpdateTime: {}", deleteDocs.getUpdateTime());
        return true;
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
