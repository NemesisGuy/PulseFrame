# API Design

## 🔌 Interface Guidelines

All API endpoints follow RESTful conventions and return standardized JSON responses via the `ApiResponseWrapper`.

## 📍 Base URL

`/api/v1`

## 🔑 Authentication

Requests must include an `Authorization: Bearer <token>` header.

## 📊 Standard Endpoints

| Method | Path           | Description           | Roles |
| :----- | :------------- | :-------------------- | :---- |
| `POST` | `/auth/login`  | Authenticate user     | GUEST |
| `GET`  | `/me/profile`  | Get current user info | USER  |
| `GET`  | `/admin/stats` | System-wide metrics   | ADMIN |

## 📦 Request/Response Example

**Request**: `POST /api/v1/resource`

```json
{
  "name": "example",
  "config": { "key": "value" }
}
```

**Response (200 OK)**:

```json
{
  "status": "success",
  "data": { "id": "uuid-123", "name": "example" },
  "errors": []
}
```

---

> [!TIP]
> Use the [API Testing Tool] or Postman collection in `docs/postman` for manual verification.

