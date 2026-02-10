package com.pulseframe.api.dto;

public class JobResponse {
    private String jobId;
    private String status;

    public JobResponse(String jobId, String status) {
        this.jobId = jobId;
        this.status = status;
    }

    public String getJobId() {
        return jobId;
    }

    public String getStatus() {
        return status;
    }
}
