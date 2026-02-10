# Unit Tests

## 🧪 Module-Level Testing

Unit tests focus on the smallest testable parts of the application in complete isolation.

## 🛠️ Tooling

- **Backend**: JUnit 5
- **Frontend**: Vitest
- **Mocking**: Mockito

## 📋 Example Coverage

| Component      | What it Tests                | Mocked Dependencies |
| :------------- | :--------------------------- | :------------------ |
| `AuthService`  | Password validation logic    | `UserRepository`    |
| `StringHelper` | Regex parsing and truncation | None                |
| `TtsStore`     | State updates after API call | `api` (Axios)       |

---

> [!TIP]
> Run unit tests with a "Watch" mode during development for immediate feedback on logic changes.

