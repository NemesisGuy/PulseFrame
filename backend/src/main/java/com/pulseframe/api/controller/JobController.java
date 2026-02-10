package com.pulseframe.api.controller;

import com.pulseframe.api.dto.JobRequest;
import com.pulseframe.api.dto.JobResponse;
import com.pulseframe.api.dto.JobStatusResponse;
import com.pulseframe.api.model.JobRecord;
import com.pulseframe.api.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<JobResponse> createJob(@Valid @RequestBody JobRequest request) {
        return ResponseEntity.ok(jobService.createJob(request));
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobStatusResponse> getJob(@PathVariable String jobId) {
        return jobService.findJob(jobId)
                .map(record -> ResponseEntity.ok(toStatusResponse(record)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private JobStatusResponse toStatusResponse(JobRecord record) {
        return new JobStatusResponse(
                record.jobId(),
                record.status().name().toLowerCase(),
                record.outputPath(),
                record.error()
        );
    }
}
