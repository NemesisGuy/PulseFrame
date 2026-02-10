package com.pulseframe.api.store;

import com.pulseframe.api.model.JobRecord;
import com.pulseframe.api.model.JobStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JobStore {
    private final Map<String, JobRecord> jobs = new ConcurrentHashMap<>();

    public JobRecord create(String jobId) {
        JobRecord record = new JobRecord(jobId, JobStatus.QUEUED, null, null);
        jobs.put(jobId, record);
        return record;
    }

    public Optional<JobRecord> get(String jobId) {
        return Optional.ofNullable(jobs.get(jobId));
    }

    public void update(String jobId, JobStatus status, String outputPath, String error) {
        jobs.put(jobId, new JobRecord(jobId, status, outputPath, error));
    }
}
