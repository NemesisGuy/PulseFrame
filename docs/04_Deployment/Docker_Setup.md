# Docker Setup

## 🐋 Container Architecture

The system is fully containerized using Docker, allowing for identical environments across development, staging, and production.

## 📁 Shared Volumes

- **`db_data`**: Persistent storage for the relational database.
- **`cache_data`**: Temporary storage for the caching layer.
- **`uploads`**: Persistent storage for user-uploaded assets.

## 🌐 Network Configuration

All containers communicate over a private bridge network (`pulseframe-net`). Only the frontend container is exposed to the host.

---

> [!IMPORTANT]
> Use `.dockerignore` files to prevent large binaries or sensitive local files from being included in image builds.

