# Docker Commands

## 🛠️ Management Commands

### Build and Start All

```bash
docker compose up -d --build
```

### Stop All

```bash
docker compose down
```

### View Logs

```bash
docker compose logs -f [SERVICE_NAME]
```

### Execute Command in Container

```bash
docker exec -it [CONTAINER_NAME] sh
```

## 🧹 Cleanup

```bash
# Remove all unused containers, networks, and images
docker system prune -a --volumes
```

