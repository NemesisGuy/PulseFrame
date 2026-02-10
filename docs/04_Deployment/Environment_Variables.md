# Environment Variables

## 📑 Variable Categories

### 🛡️ Security & Auth

- `JWT_SECRET`: Secret key for signing tokens.
- `ADMIN_PASSWORD`: Default password for the initial admin account.

### 🗄️ Database

- `DB_HOST`: Hostname of the database container.
- `DB_USER`: Database username.
- `DB_PASS`: Database password.

### 🌐 API & Networking

- `VITE_API_URL`: The public-facing URL of the backend API.
- `ALLOWED_ORIGINS`: CORS whitelist.
- `PULSEFRAME_PORT_RANGE`: Reserved local port range (4020-40300) to avoid conflicts.

---

> [!WARNING]
> Never commit `.env` files containing production secrets to version control. Always use a `.env.example` as a template.

