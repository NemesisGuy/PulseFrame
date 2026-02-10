PulseFrame Agents Implementation Blueprint

Project: PulseFrame
Author: Nemesis
Version: 0.1 (Blueprint)
Stack: CPU-only, Dockerized, Vue 3 frontend, Spring Boot backend, Redis queue, ffmpeg rendering

1. Architecture Overview

```
+----------------+        +------------------+       +----------------+
|  Vue 3 Frontend|  <---> |  Spring Boot API |  <--->| Redis Job Queue|
+----------------+        +------------------+       +----------------+
                                  |
                                  v
                          +----------------+
                          | Worker Service |
                          | (Docker + ffmpeg)
                          +----------------+
```

Key services:
- Frontend: upload, preview, configure visualizer, optional YouTube creds.
- API backend: receive requests, orchestrate jobs, return status.
- Worker: pull jobs, render video, upload optional.
- Queue: Redis for job orchestration.

2. Agent Implementation Details

2.1 TrackIngestAgent

Module/Class: TrackIngestAgent.java

Responsibilities:
- Fetch Suno track (via API) or accept user upload.
- Fetch or process Suno art or accept custom image.
- Normalize audio.
- Validate format.

Methods:
```
AudioFile ingestTrack(String trackSource, File uploadFile, String sunoTrackId)
File ingestArt(String artSource, File uploadFile)
TrackMetadata extractMetadata(File audioFile)
```

Docker: part of API container, minimal CPU usage.

Notes:
```bash
ffmpeg -i input.wav -af "loudnorm" output.wav
```

2.2 VisualizerSelectionAgent

Module/Class: VisualizerSelectionAgent.java

Responsibilities:
- Choose visualizer type.
- Build ffmpeg render parameters.

Data model:
```java
class VisualizerConfig {
    String type; // bars, waveform, circular, static_pulse
    String backgroundPath;
    int width;
    int height;
    int fps;
}
```

Example configuration:
```json
{
  "type": "bars",
  "background": "/tmp/bg.png",
  "width": 1920,
  "height": 1080,
  "fps": 30
}
```

2.3 RenderAgent

Module/Class: RenderAgent.java

Responsibilities:
- Render MP4 using ffmpeg based on config.

Methods:
```
File render(VisualizerConfig config, File audioFile)
```

ffmpeg commands per visualizer:

Bars:
```bash
ffmpeg -i track.wav -filter_complex "showspectrum=mode=separate:color=intensity:slide=scroll:scale=log,format=yuv420p" output.mp4
```

Waveform:
```bash
ffmpeg -i track.wav -filter_complex "showwaves=s=1920x1080:mode=line:colors=white,format=yuv420p" output.mp4
```

Circular (CPU, basic version):
```bash
ffmpeg -i track.wav -filter_complex "ahistogram,format=yuv420p" output.mp4
```

Static Pulse:
```bash
ffmpeg -loop 1 -i background.png -i track.wav -filter_complex "[0:v]scale=1920:1080,format=yuv420p[v];[1:a]aformat=channel_layouts=stereo[a]" -map "[v]" -map "[a]" -shortest output.mp4
```

Docker: pulseframe-worker container.

2.4 MetadataAgent

Module/Class: MetadataAgent.java

Responsibilities:
- Generate YouTube title, description, tags.
- Optional LLM integration.

Methods:
```
Metadata generateMetadata(TrackMetadata trackMetadata, Optional<String> prompt)
```

Output model:
```java
class Metadata {
    String title;
    String description;
    List<String> tags;
    File suggestedThumbnail;
}
```

2.5 UploadAgent (optional)

Module/Class: UploadAgent.java

Responsibilities:
- Upload video to YouTube via user-provided API key.

Methods:
```
String upload(File mp4File, Metadata metadata, String apiKey, String channelId, Optional<String> playlistId, String visibility)
```

Docker: inside worker or separate container if scaling uploads.

Notes:
- Use YouTube Data API v3.
- Retry up to 3 times.

2.6 JobController / QueueAgent

Module/Class: JobController.java

Responsibilities:
- Receive API requests.
- Push job to Redis queue.
- Track job progress.
- Return status and result.

Job model:
```java
class Job {
    Long userId;
    String trackSource;
    File audioFile;
    File artFile;
    VisualizerConfig visualizerConfig;
    Boolean uploadToYouTube;
    Optional<String> youtubeApiKey;
    Optional<String> channelId;
    Optional<String> playlistId;
    JobStatus status;
    File resultFile;
    Optional<String> youtubeUrl;
}
```

Endpoints:
- POST /jobs -> create job
- GET /jobs/{id}/status -> get status
- GET /jobs/{id}/result -> download MP4 or YouTube link

Queue: Redis list: pulseframe_jobs

3. Docker Services

| Service  | Container Name       | Role                                      |
|----------|----------------------|-------------------------------------------|
| Frontend | pulseframe-frontend   | Vue 3 UI                                  |
| API      | pulseframe-api        | Job orchestration, ingest agent           |
| Worker   | pulseframe-worker     | Render agent, optional upload agent       |
| Redis    | pulseframe-redis      | Job queue                                 |
| Storage  | pulseframe-storage    | Temporary or persistent MP4 storage       |

Resource allocation:
- Worker: 2 CPUs per render job, 2 to 3 concurrent jobs max.
- Storage: limit temp folder per job to 500 MB.

4. Implementation Notes

- MVP cut: 3 visualizers, MP4 export, optional YouTube upload.
- Free tier: watermark, 720p.
- Paid tier: 1080p+, no watermark, queue priority.
- Logging: each agent returns structured JSON logs.
- Error handling: agents return { status, error, retryable }.


