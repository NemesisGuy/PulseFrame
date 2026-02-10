package com.pulseframe.api.service;

import com.pulseframe.api.dto.JobRequest;
import com.pulseframe.api.dto.JobResponse;
import com.pulseframe.api.model.JobRecord;
import com.pulseframe.api.store.JobStore;
import com.pulseframe.worker.RenderWorker;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class JobService {
    private final RenderWorker renderWorker;
    private final JobStore jobStore;

    public JobService(RenderWorker renderWorker, JobStore jobStore) {
        this.renderWorker = renderWorker;
        this.jobStore = jobStore;
    }

    public JobResponse createJob(JobRequest request) {
        String jobId = UUID.randomUUID().toString();
        jobStore.create(jobId);
        renderWorker.enqueue(jobId, request);
        return new JobResponse(jobId, "queued");
    }

    public Optional<JobRecord> findJob(String jobId) {
        return jobStore.get(jobId);
    }
}
