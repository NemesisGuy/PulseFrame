package com.pulseframe.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class RenderService {
    private final String ffmpegBinary;
    private final String tempDir;

    public RenderService(
            @Value("${pulseframe.ffmpeg.binary:ffmpeg}") String ffmpegBinary,
            @Value("${pulseframe.storage.tempDir:/tmp/jobs}") String tempDir
    ) {
        this.ffmpegBinary = ffmpegBinary;
        this.tempDir = tempDir;
    }

    public Path buildOutputPath(String jobId) {
        return Path.of(tempDir, jobId, "output.mp4");
    }

    public Path render(String jobId, String trackPath, String visualizerType) throws IOException, InterruptedException {
        Path inputPath = Path.of(trackPath);
        if (!Files.exists(inputPath)) {
            throw new IllegalArgumentException("Track file not found: " + inputPath);
        }

        Path outputPath = buildOutputPath(jobId);
        Files.createDirectories(outputPath.getParent());

        String filter = resolveFilter(visualizerType);
        List<String> command = new ArrayList<>();
        command.add(ffmpegBinary);
        command.add("-y");
        command.add("-i");
        command.add(inputPath.toString());
        command.add("-filter_complex");
        command.add(filter);
        command.add(outputPath.toString());

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            throw new IllegalStateException("ffmpeg failed: " + output);
        }

        return outputPath;
    }

    private String resolveFilter(String visualizerType) {
        String type = (visualizerType == null || visualizerType.isBlank()) ? "bars" : visualizerType;
        return switch (type) {
            case "waveform" -> "showwaves=s=1920x1080:mode=line:colors=white,format=yuv420p";
            case "circular" -> "ahistogram,format=yuv420p";
            case "static_pulse" -> "showwaves=s=1920x1080:mode=cline:colors=white,format=yuv420p";
            default -> "showspectrum=mode=separate:color=intensity:slide=scroll:scale=log,format=yuv420p";
        };
    }

    public String getFfmpegBinary() {
        return ffmpegBinary;
    }
}
