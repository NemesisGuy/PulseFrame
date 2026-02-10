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

Normalize audio to peak -1dB

Validate supported formats (MP3/WAV/FLAC)

2.2 Visualizer Selection Agent

Responsibility: Choose visualizer type and prepare rendering parameters

Inputs:

user_choice: "bars" | "waveform" | "circular" | "static_pulse"

background_file_path: optional

resolution: "720p" | "1080p" | "4K"

orientation: "16:9" | "9:16"

Outputs:

visualizer_config JSON:

{
  "type": "bars",
  "background": "/tmp/bg.png",
  "width": 1920,
  "height": 1080,
  "fps": 30
}

2.3 Render Agent

Responsibility: Render audio + visualizer → MP4

Inputs:

audio_file_path

visualizer_config

Outputs:

mp4_file_path

render_logs

Notes:

CPU-friendly defaults: max 2–5 min tracks per job

Optional watermark for free tier

Example ffmpeg command (bars visualizer):

ffmpeg -i track.wav -filter_complex \
"showspectrum=mode=separate:color=intensity:slide=scroll:scale=log,format=yuv420p" \
-output video.mp4

2.4 Metadata Agent

Responsibility: Generate YouTube title, description, tags

Inputs:

track_metadata

user_prompts (optional)

Outputs:

title

description

tags

suggested_thumbnail (optional)

2.5 Upload Agent (Optional)

Responsibility: Upload video to YouTube using user-provided API key

Inputs:

mp4_file_path

title, description, tags

youtube_api_key

channel_id

playlist_id (optional)

visibility: "private" | "scheduled" | "public"

Outputs:

youtube_video_url

youtube_video_id

2.6 Job Controller / Queue Agent

Responsibility: Orchestrate agent execution, retries, and monitor job status

Inputs:

job_request object with all user selections

Outputs:

job_status: queued | running | completed | failed

result: MP4 path + optional YouTube URL

logs: execution logs

3. Job flow
User Upload / Suno Track
        │
        ▼
Track Ingest Agent
        │
        ▼
Visualizer Selection Agent
        │
        ▼
Render Agent
        │
        ▼
Metadata Agent ──► optional Upload Agent
        │
        ▼
Result returned to User (MP4 + optional YouTube URL)

4. Storage & Cleanup

Temporary files under /tmp/jobs/<job_id>

Clean up after 24h by default

Optional persistent storage for paid users

5. Error Handling

Each agent returns:

{
  "status": "success" | "failed",
  "error": "error message if failed",
  "retryable": true | false
}


Job Controller retries only retryable failures

User notified of final status

6. Config Parameters
Parameter	Default	Description
MAX_CONCURRENT_JOBS	2	CPU worker concurrency
MAX_RENDER_DURATION_MIN	5	Max track length in minutes
DEFAULT_RESOLUTION	1080p	Fallback resolution
TEMP_STORAGE_PATH	/tmp/jobs	Job temp storage
WATERMARK_ENABLED_FREE	true	Free tier watermark
YT_UPLOAD_RETRIES	3	Max upload retries
7. MVP Scope

CPU-only rendering, 3 visualizer styles

MP4 export + optional YouTube upload

Background: Suno art or 1 custom image

Minimal Vue 3 frontend + Spring Boot API backend

Single worker container, 1 concurrent job