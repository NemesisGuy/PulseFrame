# Agents Guidance

This document defines how agents are structured, what they must implement, and how they coordinate in PulseFrame. It uses the BaseDocs framework as the baseline structure and adapts it to this project.

## 1. Agent Definitions

### 1.1 Job Controller / Queue Agent

- Responsibility: Orchestrates job lifecycle, retries, and status reporting.
- Inputs: Job request payload with user selections.
- Outputs: Job status, result paths, logs, and optional YouTube URL.
- Execution: Spring Boot API service, pushes jobs to Redis queue.

### 1.2 Track Ingest Agent

- Responsibility: Fetch or receive audio and optional art; normalize and validate.
- Inputs: Track source (Suno or upload), track file or ID, art source and file.
- Outputs: Normalized audio path, art path (optional), extracted metadata.
- Execution: Spring Boot API service.

### 1.3 Visualizer Selection Agent

- Responsibility: Create render configuration based on user choices.
- Inputs: Visualizer type, resolution, orientation, background path.
- Outputs: Visualizer config JSON (type, background, width, height, fps).
- Execution: Spring Boot API service.

### 1.4 Render Agent

- Responsibility: Render MP4 using ffmpeg and the visualizer config.
- Inputs: Audio file path and visualizer config.
- Outputs: MP4 file path and render logs.
- Execution: Worker container.

### 1.5 Metadata Agent

- Responsibility: Generate YouTube title, description, and tags.
- Inputs: Track metadata and optional user prompts.
- Outputs: Title, description, tags, optional thumbnail.
- Execution: Worker container or API service.

### 1.6 Upload Agent (Optional)

- Responsibility: Upload video to YouTube using user-provided API key.
- Inputs: MP4 path, metadata, API key, channel ID, playlist ID, visibility.
- Outputs: YouTube video URL and ID.
- Execution: Worker container.

## 2. Required Agent Interface

All agents should implement a shared interface for consistency.

Example response:
```json
{
  "status": "success" | "failed",
  "error": "error message if failed",
  "retryable": true | false,
  "data": {}
}
```

Requirements:
- Always return a structured response.
- Never throw unhandled exceptions across agent boundaries.
- Include a retryable flag for controller decisions.

## 3. Execution Context

- API service handles user requests and orchestration.
- Worker service executes render and upload tasks.
- Redis queue coordinates asynchronous job execution.

## 4. Logging and Tracing

- Every agent logs a start, end, and error event.
- Include job_id and user_id in structured logs.
- Render logs are stored with the job record for debugging.

## 5. Error Handling Rules

- Transient errors (timeouts, external API failures) are retryable.
- Validation failures are not retryable.
- All external integration errors must be logged with full stack traces server-side.

## 6. File and Resource Ownership

- Temporary files live under /tmp/jobs/<job_id>.
- Cleanup runs after successful download or upload, plus a scheduled cleanup job for orphaned files.
- Paid tier can optionally persist final MP4s in object storage.

## 7. Module Placement (Proposed)

- API
  - controller/: job endpoints
  - service/: orchestration and ingest logic
  - dto/: API contracts
- Worker
  - processor/: render and upload tasks
  - ffmpeg/: command builders
  - logging/: render logs and metrics

## 8. Agent Versioning

- Agent changes that modify inputs or outputs must increment a version marker.
- Job Controller must reject incompatible job payloads with a clear error.

