# Components

## 📦 Component Breakdown

### 1. Frontend (PulseFrame-Frontend)

- **Role**: User Interface and client-side logic.
- **Key Modules**:
  - `Stores/`: State management via Pinia.
  - `Views/`: Major page layouts.
  - `Composables/`: Reusable logic.

### 2. Backend API (PulseFrame-Backend)

- **Role**: Core business logic, authentication, and data orchestration.
- **Key Modules**:
  - `Controller/`: Handles HTTP requests.
  - `Service/`: Implements domain logic.
  - `Repository/`: Database abstraction.

### 3. Worker Services (PulseFrame Worker)

- **Role**: Handles rendering and upload jobs.
- **Key Modules**:
  - `Processor/`: The core logic for task execution.
  - `Integrator/`: Connects the worker to the main API.

---

> [!NOTE]
> Each component is containerized for isolated deployment and scaling.

