# API Security

## 🛡️ Hardening Strategy

The API is protected against common vulnerabilities using several security layers.

## 🔑 Token Management

- **Stateless Auth**: JWTs contain all necessary claims to avoid session DB lookups.
- **Short Expiration**: Tokens expire in 1 hour to minimize the impact of theft.
- **Refresh Tokens**: Used to obtain new access tokens without re-authenticating.

## 🚧 Rate Limiting

- **Global Limit**: 100 requests / 10 seconds per IP.
- **Endpoint Specific**: Auth endpoints (Login/Register) are strictly limited to 5 attempts / minute to prevent brute-forcing.

## 🛡️ Input Validation

- All inputs are sanitized and validated against schemas (e.g., JSR 303 for Java, Pydantic for Python).
- **Protection**: Native resistance against SQL Injection, XSS, and CSRF.

---

> [!IMPORTANT]
> Always use HTTPS in production to ensure end-to-end encryption of sensitive API payloads.

