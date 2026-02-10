# Settings

## ⚙️ Application Parameters

These settings control the runtime behavior of the system and can often be adjusted without code changes.

### 🏠 System Settings

| Key                   | Default | Description                                |
| :-------------------- | :------ | :----------------------------------------- |
| `maintenance_mode`    | `false` | Disables all non-admin write operations.   |
| `max_page_size`       | `100`   | Global pagination limit for API responses. |
| `session_timeout_min` | `60`    | Duration before a JWT expires.             |

### 🛠️ Service-Specific Settings

- **Job Poll Interval**: How often the scheduler checks for new jobs.
- **Log Retention Days**: Retention period for archived logs.

---

> [!NOTE]
> Most settings are loaded from the database at startup and cached in-memory for performance.

