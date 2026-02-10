# Limits and Parameters

## 🚦 Operational Constraints

To ensure system stability, the following hard limits are enforced.

### 💻 Resource Allocations

- **Max Memory (API)**: 2 GB
- **Max CPU (Worker)**: 2 vCPU

### 📤 Data Limits

- **Max File Upload Size**: 200 MB
- **Max Jobs per User**: 2 concurrent
- **Max Text Input Length**: 1000 characters

## ⏳ Timeouts

- **API Request Timeout**: 30s
- **Job Processing Timeout**: 10 minutes
- **Database Connection Timeout**: 5s

---

> [!CAUTION]
> Exceeding these limits will trigger an immediate error response to prevent cascading failures.

