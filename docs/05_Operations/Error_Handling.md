# Error Handling

## 🛡️ Resilience Strategy

The system implements a multi-layered error handling approach to ensure stability under failure conditions.

## 🔄 Retry Policy

- **Transient Errors**: 503 Service Unavailable, network timeouts are retried up to 3 times with exponential backoff.
- **Client Errors**: 400 Bad Request, 401 Unauthorized are _not_ retried and return immediate feedback to the user.

## 📋 Exception Mapping

| Exception                      | HTTP Status             | User Message          |
| :----------------------------- | :---------------------- | :-------------------- |
| `InsufficientCreditsException` | `402 Payment Required`  | "Not enough credits." |
| `RateLimitExceededException`   | `429 Too Many Requests` | "Slow down."          |
| `EntityNotFoundException`      | `404 Not Found`         | "Resource missing."   |

## 🧪 Circuit Breakers

For critical external integrations, a **Circuit Breaker** pattern is used to stop requests to a failing provider and allow it to recover.

---

> [!CAUTION]
> Always log the full stack trace on the server for `500 Internal Server Error`, but obfuscate technical details in the response sent to the client.

