# Agents and Modules

## 🤖 Agent Definitions

The system utilizes specialized "Agents" to handle discrete domain tasks.

### 1. Job Controller Agent

- **Responsibility**: Manages the lifecycle of incoming jobs.
- **Inputs**: Job specifications from the user.
- **Outputs**: Status updates and enrollment in the job queue.

### 2. Render Agent

- **Responsibility**: Performs the computationally intensive work.
- **Execution Environment**: Isolated Docker container.

## 🧩 Module Breakdown

- **`com.voxbox.app.service`**: Business logic encapsulation.
- **`com.voxbox.app.repository`**: JPA-based data access.
- **`com.voxbox.app.dto`**: Data Transfer Objects for API contracts.

---

> [!NOTE]
> Each agent must implement the base `AgentInterface` for consistent error handling and logging.

