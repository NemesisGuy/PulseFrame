package com.pulseframe.api.model;

public record JobRecord(String jobId, JobStatus status, String outputPath, String error) {
}
