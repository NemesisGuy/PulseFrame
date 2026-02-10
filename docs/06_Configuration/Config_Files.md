# Configuration Files

## 📂 File Locations

| Service            | File Path                                 | Format |
| :----------------- | :---------------------------------------- | :----- |
| **Backend**        | `src/main/resources/application-prod.yml` | YAML   |
| **Frontend**       | `.env.production`                         | Dotenv |
| **Infrastructure** | `docker-compose.yml`                      | YAML   |

## 🛠️ Key Config Sections

### Backend (application.yml)

- **`spring.datasource`**: Connection pooling and driver settings.
- **`pulseframe.security`**: JWT and CORS configuration.

### Frontend (.env)

- **`VITE_API_URL`**: Backend connectivity.
- **`VITE_SUPPORT_EMAIL`**: Contact point for users.

---

> [!TIP]
> Use profiles (e.g., `dev`, `test`, `prod`) to manage different configuration sets without changing the core code.

