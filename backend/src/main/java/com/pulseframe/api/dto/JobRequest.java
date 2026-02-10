package com.pulseframe.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class JobRequest {
    @NotBlank
    private String trackSource;

    @NotBlank
    private String trackPath;

    @Pattern(regexp = "bars|waveform|circular|static_pulse")
    private String visualizerType;

    public String getTrackSource() {
        return trackSource;
    }

    public void setTrackSource(String trackSource) {
        this.trackSource = trackSource;
    }

    public String getTrackPath() {
        return trackPath;
    }

    public void setTrackPath(String trackPath) {
        this.trackPath = trackPath;
    }

    public String getVisualizerType() {
        return visualizerType;
    }

    public void setVisualizerType(String visualizerType) {
        this.visualizerType = visualizerType;
    }
}
