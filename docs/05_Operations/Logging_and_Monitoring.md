# Logging and Monitoring

## 📋 Logging Strategy

Logs are standardized across all services in JSON format.

### 🔍 Log Levels

- **INFO**: Standard operational events.
- **WARN**: Unexpected events that don't stop the system.
- **ERROR**: Critical failures requiring immediate attention.

## 📊 Monitoring Stack

- **Prometheus**: Metrics collection.
- **Grafana**: Dashboard visualization.
- **Loki/Promtail**: Log aggregation.

## 🚨 Alerts

Alerts are triggered for:

- **service downtime**
- **high error rates (> 5%)**
- **disk usage (> 80%)**

