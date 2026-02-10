# Data Flow

## 🌊 Core Request Flow

This section describes how a user request traverses the system.

```mermaid
sequenceDiagram
    participant U as User
    participant F as Frontend
    participant B as Backend
    participant D as Database
    U->>F: Triggers Action (e.g., Click Button)
    F->>B: POST /api/v1/resource (with Auth Token)
    B->>D: validate & persist
    D-->>B: success
    B-->>F: 201 Created (JSON Response)
    F-->>U: Update UI (Reactive State)
```

## 🛠️ Async Processing Flow

For long-running tasks:

1. **Frontend**: Submits request to API.
2. **Backend**: Enrolls task in Queue and returns `202 Accepted`.
3. **Worker**: Picks up task from Queue and executes.
4. **Data Store**: Worker updates status in database.
5. **Frontend**: Polls or receives notification (WebSocket) of completion.

## 💾 Storage Layer

- **Relational**: PostgreSQL for structured data.
- **Cache**: Redis for session and temporary states.
- **Object Storage**: MinIO for files and binary blobs.

