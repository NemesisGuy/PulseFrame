package com.pulseframe.worker;

import com.pulseframe.api.dto.JobRequest;
import com.pulseframe.api.model.JobStatus;
import com.pulseframe.api.service.RenderService;
import com.pulseframe.api.store.JobStore;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class RenderWorker {
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(2);

    private final RenderService renderService;
    private final JobStore jobStore;

    public RenderWorker(RenderService renderService, JobStore jobStore) {
        this.renderService = renderService;
        this.jobStore = jobStore;
    }

    public void enqueue(String jobId, JobRequest request) {
        EXECUTOR.submit(() -> runJob(jobId, request));
    }

    private void runJob(String jobId, JobRequest request) {
        jobStore.update(jobId, JobStatus.RUNNING, null, null);
        try {
            Path outputPath = renderService.render(jobId, request.getTrackPath(), request.getVisualizerType());
            jobStore.update(jobId, JobStatus.COMPLETED, outputPath.toString(), null);
        } catch (Exception ex) {
            jobStore.update(jobId, JobStatus.FAILED, null, ex.getMessage());
        }
    }
}
