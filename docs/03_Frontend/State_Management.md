# State Management

## 🧪 Store Architecture

The frontend uses **Pinia** for centralized reactive state management.

### Global Stores

1. **`auth`**: Manages JWT tokens, user sessions, and login/logout flows.
2. **`ui`**: Tracks modal states, sidebar toggles, and theme preferences.
3. **`data`**: Buffers API responses and handles reactive refetching.

## 🔄 Reactivity Patterns

- **Watchers**: Automatically sync `localStorage` with store state (e.g., access tokens).
- **Computed Properties**: Derive UI states (e.g., button disabled status) from raw data.
- **Actions**: Wrap asynchronous API calls with loading state management.

---

> [!IMPORTANT]
> Ensure all API-connected stores use the standardized `useApi` composable for consistent header management (e.g., Authorization).

