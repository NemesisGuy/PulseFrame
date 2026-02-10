# CI/CD

## 🚀 Automation Pipeline

Our Continuous Integration and Continuous Deployment (CI/CD) pipelines ensure every change is tested and deployed reliably.

## 🛠️ Tools

- **GitHub Actions**
- **Docker Hub / GitHub Container Registry**
- **Terraform**

## 🌊 Pipeline Stages

1. **Lint & Format**: Ensures code adheres to standard styles.
2. **Unit Tests**: Rapid verification of logic modules.
3. **Build**: Generates Docker images and pushes to the registry.
4. **Integration/E2E**: Full-stack verification in a staging-like container.
5. **Deploy**: Rolling update to the production environment.

---

> [!IMPORTANT]
> A failure in any stage (except optionally Lint) must block the subsequent stages to prevent broken code from reaching production.

