# Integration Tests

## 🔗 Component Interaction

Integration tests verify that different modules of the system work together correctly, including database persistence and internal service calls.

## 🛠️ Testing Environment

- **Database**: Uses an in-memory H2 or a dedicated test container.
- **Context**: Loads only the necessary service layer and its immediate dependencies.

## 📋 Critical Scenarios

- **Persistence**: Can the repository save and retrieve a complex entity?
- **Controller Context**: Does the API return the correct HTTP status for a valid/invalid request?
- **Workflow**: Does one service correctly trigger another (e.g., `PaymentService` -> `CreditService`)?

---

> [!NOTE]
> Integration tests should be slower than unit tests but much faster than full End-to-End tests.

