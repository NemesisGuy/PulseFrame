# Testing Strategy

## 🧪 Testing Pyramid

We maintain high quality through a multi-tiered testing approach.

### 1. Unit Tests

- **Scope**: Individual functions and components.
- **Frequency**: On every commit.
- **Tool**: JUnit 5.

### 2. Integration Tests

- **Scope**: Service-to-Service or Store-to-API interactions.
- **Frequency**: CI/CD pipeline.
- **Tool**: JUnit 5 + MockMvc.

### 3. End-to-End (E2E) Tests

- **Scope**: Critical user workflows (Login -> Action -> Verification).
- **Environment**: Dockerized "prod-like" environment.
- **Tool**: Playwright.

---

> [!TIP]
> Aim for at least **80% line coverage** in core business logic services.

