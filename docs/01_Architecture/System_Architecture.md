# System Architecture

## 🧭 High-Level Overview

The **PulseFrame** architecture follows a modular, N-Tier pattern. It is designed for maximum separation of concerns (SoC) and horizontal scalability.

## 🗺️ Architectural Diagram

```mermaid
graph TD
    User([User]) --> Frontend[Frontend Vue 3]
    Frontend --> API[API Gateway / Service Layer]
    API --> ServiceA[Service A]
    API --> ServiceB[Service B]
    ServiceA --> DB[(Primary Database)]
    ServiceB --> Queue[Job Queue]
    Queue --> Worker[Asynchronous Worker]
```

## 📐 Design Patterns

- **Repository Pattern**: Decouples business logic from data access.
- **Observer Pattern**: Handles asynchronous event-driven updates.
- **Factory Pattern**: Manages the creation of complex service instances.

---

> [!IMPORTANT]
> Adhere to the `ARCHITECTURE_RULES.md` for all new structural changes to maintain consistency.

