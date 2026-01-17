# AGENTS.md - Stores Directory

## Overview
The `stores` directory contains Pinia store modules that manage application state. Stores handle reactive state, business logic, and coordinate between API services and components.

## Directory Structure
- **auth-user.ts**: Authentication state and user session management
- **current-channel-tasks.ts**: Tasks for the currently selected channel
- **current-organization.ts**: Currently selected organization state
- **index.ts**: Central export file for all stores

## Store Architecture

### Pinia Pattern
Stores use Pinia's composition API style with `defineStore`:
```typescript
export const useStoreName = defineStore('storeName', () => {
    // State (refs)
    const state = ref(initialValue);
    
    // Computed (computed)
    const derivedState = computed(() => state.value.something);
    
    // Actions (functions)
    async function action() {
        // Business logic
    }
    
    // Return public API
    return { state, derivedState, action };
});
```

## Store Files

### auth-user.ts
Manages authentication state:
- **State**:
  - `accessToken`: JWT access token
  - `accessTokenExpiresAt`: Token expiration timestamp
  - `errorMessage`: Authentication error messages
- **Actions**:
  - `requestAuthentication(email, password)`: Login action
- **Features**:
  - Persists token to localStorage
  - Watches token changes for auto-persistence
  - Integrates with authentication API service

### current-channel-tasks.ts
Manages tasks for the active channel:
- **State**:
  - Task list for current channel
  - Task filters and sorting
  - Loading/error states
- **Actions**:
  - Fetch tasks for channel
  - Create/update/delete tasks
  - Change task status
- **Features**:
  - Reactive task updates
  - Optimistic UI updates
  - Task filtering and grouping

### current-organization.ts
Manages currently selected organization:
- **State**:
  - Current organization data
  - Organization members
  - Organization settings
- **Actions**:
  - Select organization
  - Fetch organization details
  - Update organization
- **Features**:
  - Organization context for entire app
  - Member management
  - Permission checking

## Using Stores

### In Components
```vue
<script setup lang="ts">
import { useAuthUserStore } from '@/stores';

const authStore = useAuthUserStore();

async function handleLogin() {
    await authStore.requestAuthentication(email, password);
    if (authStore.accessToken) {
        // Navigate to dashboard
    }
}
</script>
```

### In Other Stores
Stores can use other stores:
```typescript
import { useAuthUserStore } from './auth-user';

export const useSomeStore = defineStore('some', () => {
    const authStore = useAuthUserStore();
    
    async function protectedAction() {
        if (!authStore.accessToken) {
            throw new Error('Not authenticated');
        }
        // Use token for API call
    }
});
```

## State Management Patterns

### Reactive State
- Use `ref()` for primitive values
- Use `reactive()` for objects/arrays
- Use `computed()` for derived state

### Async Actions
- Always use async/await for API calls
- Handle loading states
- Handle error states
- Provide user feedback

### State Persistence
- Use localStorage for user preferences
- Use sessionStorage for temporary state
- Watch state changes with Vue's `watch()`
- Clear storage on logout

## API Integration

### Calling API Services
```typescript
import { useTasksApiService } from '@/api-services';

export const useTasksStore = defineStore('tasks', () => {
    const apiService = useTasksApiService();
    const tasks = ref([]);
    
    async function fetchTasks() {
        const result = await apiService.getTasks();
        if (result.isOk()) {
            tasks.value = result.value;
        }
    }
    
    return { tasks, fetchTasks };
});
```

### Error Handling
- Always check `result.isOk()` before accessing data
- Store error messages in state for UI display
- Provide meaningful error messages to users
- Log errors for debugging

## Adding New Stores

1. Create a new `.ts` file with descriptive name
2. Import `defineStore` from Pinia
3. Define store ID (string identifier)
4. Implement state using refs/reactive
5. Implement computed properties
6. Implement actions
7. Return public API from store function
8. Export store using `useStoreName` convention
9. Add export to `index.ts`

## Best Practices

### Store Scope
- Keep stores focused on a single domain
- Split large stores into smaller, focused ones
- Use composition when stores need to share logic

### State Normalization
- Normalize nested data structures
- Avoid deep nesting in state
- Use IDs to reference related entities

### Computed vs State
- Use computed for derived values
- Don't store values that can be calculated
- Memoize expensive computations

### Actions vs Direct Mutations
- Always use actions to modify state
- Don't mutate state directly from components
- Actions provide centralized logic

## Testing
- Mock API services in store tests
- Test state changes after actions
- Test computed properties
- Test error handling paths

## Dependencies
- Pinia: State management library
- Vue: Refs, computed, watch functions
- API Services: Backend communication
- Vue Router: Navigation (some stores)

## Store Lifecycle
- Stores are created on first use
- State persists until page reload (unless using localStorage)
- Can be reset manually using store methods
- Access tokens should be cleared on logout
