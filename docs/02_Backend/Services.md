# Services

## 🏗️ Service Architecture

Services bridge the gap between API controllers and the data layer, encapsulating the core business rules.

## 📋 Primary Services

### 1. IdentityService

- **Responsibility**: Password hashing, token generation, and role validation.
- **Dependencies**: `UserRepository`.

### 2. StorageService

- **Responsibility**: Handling file uploads/downloads with MinIO.
- **Dependencies**: `ObjectStorageClient`.

### 3. NotificationService

- **Responsibility**: Dispatching emails, SMS, or in-app alerts.
- **Dependencies**: `SmtpGateway`.

---

> [!TIP]
> Use Dependency Injection (DI) to inject services into controllers, ensuring they remain decoupled and testable.

