# End-to-End Tests

## 🏁 E2E Test Cases

E2E tests verify that all system components (Frontend, Backend, DB, Cache) work correctly as a unified system.
Playwright is used for automated E2E coverage across critical user workflows.

### Case 1: Standard User Workflow

1. Browse to landing page.
2. Register a new account.
3. Perform a core action (e.g., render visualizer).
4. Verify result in output list.

### Case 2: Administrative Hardening

1. Login as Admin.
2. Update a global system setting.
3. Logout and login as User.
4. Verify the setting effect from a user perspective.

## 🚀 Running E2E Tests

```bash
# Start the Docker environment
docker compose up -d

# Run the test suite
npm run test:e2e
```

