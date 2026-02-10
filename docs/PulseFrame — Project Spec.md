PulseFrame — Project Spec

Codename: PulseFrame
Author: Nemesis
Version: 0.1 (MVP)
Stack assumptions: CPU-only rendering, Dockerized, Vue 3 + Spring Boot API backend, optional YouTube API key

1. Overview

PulseFrame is a tool that converts AI-generated music (e.g., Suno) into visualizer videos, optionally generating metadata and uploading directly to YouTube. It allows users to:

Export MP4 visualizer videos

Optionally auto-upload to YouTube using their own API key

Choose background art (from Suno or custom)

Select from multiple visualizer styles

Customize video resolution, orientation, and metadata

PulseFrame is modular, with each “agent” performing a single responsibility. Agents can run chained for automation or independently for user control.

2. Agents
2.1 Track Ingest Agent

Responsibility: Fetch / receive audio track + optional art. Prepare for rendering.

Inputs:

track_source: "suno" | "upload"

track_file: audio file if "upload"

track_id: Suno ID if "suno"

art_source: "suno" | "upload" | "none"

art_file: image file if "upload"

Outputs:

audio_file_path

art_file_path (optional)

track_metadata (title, artist, duration, BPM)

Notes:

PulseFrame - Project Spec

Codename: PulseFrame
Author: Nemesis
Version: 0.1 (MVP)
Stack: CPU-only rendering, Dockerized, Vue 3 frontend, Spring Boot API backend, optional YouTube API key

1. Overview

PulseFrame converts AI-generated music (for example Suno tracks) into visualizer videos and can optionally upload to YouTube. The system is modular: each agent has a single responsibility and can run in a chain or independently.

Primary capabilities:
- Export MP4 visualizer videos.
- Optional YouTube upload using the user's API key.
- Background art from Suno or custom image.
- Multiple visualizer styles.
- Custom resolution, orientation, and metadata.

2. Agents

2.1 Track Ingest Agent

Responsibility: Fetch or receive audio and optional art, then prepare for rendering.

Inputs:
- track_source: "suno" | "upload"
- track_file: audio file if "upload"
- track_id: Suno ID if "suno"
- art_source: "suno" | "upload" | "none"
- art_file: image file if "upload"

Outputs:
- audio_file_path
- art_file_path (optional)
- track_metadata (title, artist, duration, BPM)

Notes:
- Normalize audio to peak -1 dB.
- Validate formats: MP3, WAV, FLAC.

2.2 Visualizer Selection Agent

Responsibility: Choose visualizer type and produce render parameters.

Inputs:
- user_choice: "bars" | "waveform" | "circular" | "static_pulse"
- background_file_path: optional
- resolution: "720p" | "1080p" | "4K"
- orientation: "16:9" | "9:16"

Outputs:
- visualizer_config

Example:
```json
{
        "type": "bars",
        "background": "/tmp/bg.png",
        "width": 1920,
        "height": 1080,
        "fps": 30
}
```

2.3 Render Agent

Responsibility: Render audio + visualizer into MP4.

Inputs:
- audio_file_path
- visualizer_config

Outputs:
- mp4_file_path
- render_logs

Notes:
- CPU-friendly defaults: max 2 to 5 min tracks per job.
- Optional watermark for free tier.

Example command (bars):
```bash
ffmpeg -i track.wav -filter_complex \
"showspectrum=mode=separate:color=intensity:slide=scroll:scale=log,format=yuv420p" \
output.mp4
```

2.4 Metadata Agent

Responsibility: Generate YouTube title, description, and tags.

Inputs:
- track_metadata
- user_prompts (optional)

Outputs:
- title
- description
- tags
- suggested_thumbnail (optional)

2.5 Upload Agent (optional)

Responsibility: Upload video to YouTube using user-provided API key.

Inputs:
- mp4_file_path
- title, description, tags
- youtube_api_key
- channel_id
- playlist_id (optional)
- visibility: "private" | "scheduled" | "public"

Outputs:
- youtube_video_url
- youtube_video_id

2.6 Job Controller / Queue Agent

Responsibility: Orchestrate agent execution, retries, and job monitoring.

Inputs:
- job_request with all user selections

Outputs:
- job_status: queued | running | completed | failed
- result: MP4 path + optional YouTube URL
- logs: execution logs

3. Job Flow

User Upload / Suno Track
        -> Track Ingest Agent
        -> Visualizer Selection Agent
        -> Render Agent
        -> Metadata Agent
        -> Optional Upload Agent
        -> Result (MP4 + optional YouTube URL)

4. Storage and Cleanup

- Temporary files under /tmp/jobs/<job_id>
- Cleanup after 24 hours by default
- Optional persistent storage for paid users

5. Error Handling

Agent response:
```json
{
        "status": "success" | "failed",
        "error": "error message if failed",
        "retryable": true | false
}
```

Rules:
- Job Controller retries only retryable failures.
- User receives final status and error message.

6. Config Parameters

| Parameter               | Default | Description                 |
|------------------------|---------|-----------------------------|
| MAX_CONCURRENT_JOBS     | 2       | CPU worker concurrency      |
| MAX_RENDER_DURATION_MIN | 5       | Max track length in minutes |
| DEFAULT_RESOLUTION      | 1080p   | Fallback resolution         |
| TEMP_STORAGE_PATH       | /tmp/jobs | Job temp storage          |
| WATERMARK_ENABLED_FREE  | true    | Free tier watermark         |
| YT_UPLOAD_RETRIES       | 3       | Max upload retries          |

7. MVP Scope

- CPU-only rendering, 3 visualizer styles
- MP4 export + optional YouTube upload
- Background: Suno art or 1 custom image
- Minimal Vue 3 frontend + Spring Boot API backend
- Single worker container, 1 concurrent job

8. Storage Strategy (MVP)

Temporary storage only (recommended):
- Render -> use -> discard
- Worker writes MP4 to /tmp/<job_id>/video.mp4
- Delete after download or upload
- Cleanup job for orphaned work older than 1 to 2 hours

Example cleanup:
```bash
rm -rf /tmp/jobs/<job_id>
```

Java example:
```java
Files.deleteIfExists(Path.of("/tmp/jobs/" + jobId));
```

Optional paid tier storage:
- S3 or MinIO
- Store final MP4s, purge after 7 to 30 days

Storage limits:

| Resolution | Max Track Length | Approx Size | Notes     |
|------------|------------------|-------------|-----------|
| 720p       | 5 min            | 100-150 MB  | Free tier |
| 1080p      | 5 min            | 300-400 MB  | Paid tier |
| 4K         | 5 min            | ~1 GB       | Rare, paid |

Worker design tweaks:
- No permanent storage in worker container; mount /tmp only.
- Optional: stream directly to YouTube API to avoid full disk writes.
