package com.pulseframe.api.dto;

public class JobStatusResponse {
    private final String jobId;
    private final String status;
    private final String outputPath;
    private final String error;

    public JobStatusResponse(String jobId, String status, String outputPath, String error) {
        this.jobId = jobId;
        this.status = status;
        this.outputPath = outputPath;
        this.error = error;
    }

    public String getJobId() {
        return jobId;
    }

    public String getStatus() {
        return status;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getError() {
        return error;
    }
}
