# Job Queue Workflow

## 🏗️ Execution Model

The system uses a Redis queue to process long-running tasks asynchronously.

## 🔄 Lifecycle of a Job

1. **Enrollment**: API receives a request and creates a `PENDING` job record.
2. **Scheduling**: The Job Scheduler assigns the job to an available worker.
3. **Execution**: The worker updates the status to `RUNNING` and processes the task.
4. **Finalization**: On success, status becomes `COMPLETED`. On failure, status becomes `FAILED` with an error message stored.

## 🚦 Concurrency & Limits

- **Max Workers**: 2
- **Max Retries**: 3
- **Timeout**: 300s

---

> [!TIP]
> Use the `Operations/Monitoring.md` tools to track queue depth and average processing time.

