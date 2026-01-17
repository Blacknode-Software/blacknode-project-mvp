# AGENTS.md - Router Directory

## Overview
The `router` directory contains Vue Router configuration that defines application routes, navigation, and route guards.

## Directory Structure
- **index.ts**: Main router configuration file with route definitions

## Router Configuration

### Route Structure
The router uses Vue Router 4 with history mode:
```typescript
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        // Route definitions
    ],
});
```

## Defined Routes

### Authentication Routes
- **`/login`**: Login page for user authentication
  - Component: `LoginView`
  - Public route (no authentication required)

### Application Routes
All application routes follow the pattern: `/{view}/:organization_id/:channel_id`

- **`/list/:organization_id/:channel_id`**: Tasks list view
  - Component: `TasksListView`
  - Displays tasks in a list format
  
- **`/kanban/:organization_id/:channel_id`**: Kanban board view
  - Component: `KanbanBoardView`
  - Displays tasks in a Kanban board layout

- **`/settings/:organization_id/:channel_id`**: Channel settings
  - Component: `ChannelSettingsView`
  - Manage channel-specific settings

- **`/settings-main/:organization_id/:channel_id`**: Main settings
  - Component: `SettingsView`
  - Manage general application settings

### Fallback Route
- **`/:pathMatch(.*)*`**: Catch-all redirect
  - Component: `RedirectView`
  - Handles undefined routes and redirects appropriately

## Route Parameters

### Organization ID
- Required parameter in most routes
- Identifies the current organization context
- Used to fetch organization-specific data

### Channel ID
- Required parameter in most routes
- Identifies the current channel/project context
- Used to fetch channel-specific tasks and data

## Navigation

### Programmatic Navigation
```typescript
import { useRouter } from 'vue-router';

const router = useRouter();

// Navigate to kanban board
router.push({
    name: 'Tasks Kanban board',
    params: {
        organization_id: '123',
        channel_id: '456',
    },
});
```

### Declarative Navigation
```vue
<template>
    <RouterLink 
        :to="{
            name: 'Tasks list',
            params: { organization_id: orgId, channel_id: channelId }
        }"
    >
        View Tasks
    </RouterLink>
</template>
```

## Route Guards

### Authentication Guard (To Be Implemented)
Currently, authentication checking is commented out in stores. When implementing:
```typescript
router.beforeEach((to, from, next) => {
    const authStore = useAuthUserStore();
    
    if (to.name !== 'Login' && !authStore.accessToken) {
        next({ name: 'Login' });
    } else {
        next();
    }
});
```

### Navigation Guards (Future)
Consider implementing:
- Organization access validation
- Channel access validation
- Permission-based route protection

## Route Meta Information

### Future Enhancement
Add meta fields for:
- `requiresAuth`: Boolean for authentication requirement
- `permissions`: Required permissions array
- `title`: Page title for document.title updates
- `breadcrumb`: Breadcrumb navigation data

Example:
```typescript
{
    path: '/kanban/:organization_id/:channel_id',
    name: 'Tasks Kanban board',
    component: KanbanBoardView,
    meta: {
        requiresAuth: true,
        title: 'Kanban Board',
    },
}
```

## Adding New Routes

1. Import the view component at the top of `index.ts`
2. Add route definition to the `routes` array:
```typescript
{
    path: '/your-path/:org_id/:channel_id',
    name: 'Your Route Name',
    component: YourViewComponent,
}
```
3. Ensure route parameters match existing patterns
4. Add route guards if needed
5. Update navigation components to include new route

## Route Naming Conventions
- Use descriptive names (e.g., 'Tasks Kanban board')
- Names should match the view's purpose
- Use consistent casing (Title Case)

## URL Structure Patterns

### Current Pattern
```
/{view-type}/{organization_id}/{channel_id}
```

### Benefits
- Clear context hierarchy
- Easy parameter extraction
- Consistent structure across views

### Considerations
- All routes require org and channel context
- Login is the only route without these parameters
- Redirect view handles missing/invalid routes

## Lazy Loading (Future Optimization)

For better performance, consider lazy loading routes:
```typescript
{
    path: '/kanban/:organization_id/:channel_id',
    name: 'Tasks Kanban board',
    component: () => import('@/views/KanbanBoardView.vue'),
}
```

## Testing Router
- Test route matching
- Test parameter extraction
- Test navigation guards
- Test redirect logic

## Dependencies
- Vue Router 4
- View components from `@/views`
- Auth store for guards (when implemented)

## Integration Points
- Used in `main.ts` to initialize app
- Accessed via `useRouter()` in components
- `<RouterView />` in App.vue for rendering
- `<RouterLink />` for navigation links
