# Auth and Roles

## 🔐 Identity Management

The system uses **JWT (JSON Web Tokens)** for stateless authentication.

## 👤 Role Hierarchy

1. **GUEST**: Unauthenticated access to public routes (Login/Register).
2. **USER**: Standard access to core features (Synthesis, Profile).
3. **ADMIN**: Full access to management tools (Users, System Config).

## 🛡️ Permission Mapping

| Resource   | GUEST | USER | ADMIN |
| :--------- | :---- | :--- | :---- |
| `/auth/*`  | ✅    | ✅   | ✅    |
| `/tts/*`   | ❌    | ✅   | ✅    |
| `/admin/*` | ❌    | ❌   | ✅    |

---

> [!IMPORTANT]
> Roles are embedded in the JWT claims and verified on every request by the backend `SecurityFilter`.

