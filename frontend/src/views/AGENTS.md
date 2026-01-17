# AGENTS.md - Views Directory

## Overview
The `views` directory contains page-level Vue components that represent distinct pages/routes in the application. Views are mounted by Vue Router and typically compose multiple smaller components.

## Directory Structure
- **LoginView.vue**: User authentication page
- **RegisterView.vue**: User registration page
- **KanbanBoardView.vue**: Kanban-style task board view
- **TasksListView.vue**: List-style task view
- **ChannelSettingsView.vue**: Channel configuration page
- **SettingsView.vue**: Main application settings page
- **RedirectView.vue**: Route handling and redirection logic

## View Components

### LoginView.vue
Authentication page:
- Email and password input fields
- Login form submission
- Error message display
- Integration with `auth-user` store
- Redirects to dashboard on success

### RegisterView.vue
User registration:
- New user account creation
- Form validation
- Registration API integration
- Redirect to login or dashboard

### KanbanBoardView.vue
Kanban task board interface:
- Multiple columns for task statuses (To Do, In Progress, Done, etc.)
- Drag-and-drop task movement
- Task cards with details
- Uses `KanbanColumn` components
- Real-time task updates
- Filtered by current channel

### TasksListView.vue
List-based task view:
- Linear list of tasks
- Sorting and filtering options
- Task grouping by status or priority
- Quick task creation
- Task status updates
- Uses `TasksGroup` components

### ChannelSettingsView.vue
Channel management:
- Channel name and description editing
- Channel member management
- Permission settings
- Channel deletion
- Integration settings

### SettingsView.vue
Main application settings:
- User profile settings
- Notification preferences
- Theme/appearance settings
- Account management
- Organization settings access

### RedirectView.vue
Route management:
- Handles undefined routes
- Redirects based on authentication state
- Default organization/channel selection
- Entry point routing logic

## View Architecture

### Component Structure
```vue
<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { ComponentA, ComponentB } from '@/components';
import { useStore } from '@/stores';

// Component logic
const router = useRouter();
const store = useStore();

onMounted(() => {
    // Fetch data
});
</script>

<template>
    <div class="view-container">
        <!-- View template -->
    </div>
</template>

<style scoped>
/* View-specific styles */
</style>
```

### View Characteristics
- Views are route-level components
- They orchestrate multiple smaller components
- They handle route parameters
- They fetch data on mount
- They manage page-level state

## Data Fetching

### OnMounted Pattern
```typescript
onMounted(async () => {
    const { organization_id, channel_id } = route.params;
    await store.fetchData(organization_id, channel_id);
});
```

### Route Parameter Access
```typescript
import { useRoute } from 'vue-router';

const route = useRoute();
const orgId = route.params.organization_id;
const channelId = route.params.channel_id;
```

## Navigation Between Views

### Using Router
```typescript
import { useRouter } from 'vue-router';

const router = useRouter();

function goToSettings() {
    router.push({
        name: 'Channel settings',
        params: {
            organization_id: currentOrg.value,
            channel_id: currentChannel.value,
        },
    });
}
```

### Using RouterLink
```vue
<template>
    <RouterLink 
        :to="{ 
            name: 'Tasks list',
            params: { organization_id: orgId, channel_id: channelId }
        }"
    >
        Switch to List View
    </RouterLink>
</template>
```

## State Management in Views

### Using Stores
Views should use Pinia stores for:
- Fetching data from API
- Managing reactive state
- Sharing data with child components
- Persisting user actions

```typescript
import { useTasksStore } from '@/stores';

const tasksStore = useTasksStore();

onMounted(() => {
    tasksStore.fetchTasks(channelId);
});
```

### Local State
Use local refs for:
- UI-only state (modals, dropdowns)
- Form inputs
- Temporary data

```typescript
const isModalOpen = ref(false);
const searchQuery = ref('');
```

## View Layouts

### Using Layout Components
```vue
<template>
    <MainView>
        <template #header>
            <MainHeader />
        </template>
        <template #sidebar>
            <MainSidebar />
        </template>
        <template #content>
            <!-- View-specific content -->
        </template>
    </MainView>
</template>
```

## Adding New Views

1. Create a new `.vue` file in the `views` directory
2. Use the standard view structure (script setup, template, style)
3. Import necessary components and stores
4. Implement data fetching in `onMounted`
5. Add route definition in `router/index.ts`
6. Link to the view from navigation components

## View Naming Conventions
- Use `ViewName.vue` format (e.g., `KanbanBoardView.vue`)
- Name should describe the page purpose
- Use PascalCase for file names
- Export as default component

## Styling Views

### Scoped Styles
```vue
<style scoped>
.view-container {
    /* View-specific styles */
}
</style>
```

### Global Styles
- Import from `@/assets/main.css` when needed
- Avoid adding global styles in view components
- Use CSS classes consistently

## Responsive Design
- Views should be responsive
- Use CSS Grid/Flexbox for layouts
- Test on different screen sizes
- Consider mobile navigation patterns

## Performance Considerations

### Code Splitting
Views are automatically code-split by Vue Router when using lazy loading.

### Data Loading
- Show loading states during data fetch
- Handle empty states gracefully
- Display errors to users
- Implement pagination for large datasets

## Testing Views
- Test route parameter handling
- Test data fetching logic
- Test user interactions
- Mock API calls and stores
- Test navigation flows

## Dependencies
- Vue 3 (Composition API)
- Vue Router (navigation and route params)
- Pinia stores (state management)
- Component imports from `@/components`
- Layout imports from `@/layout`

## Common Patterns

### Authentication Check
```typescript
import { useAuthUserStore } from '@/stores';

const authStore = useAuthUserStore();

onMounted(() => {
    if (!authStore.accessToken) {
        router.push('/login');
    }
});
```

### Error Handling
```typescript
const error = ref<string | null>(null);

async function loadData() {
    try {
        await store.fetchData();
    } catch (e) {
        error.value = 'Failed to load data';
    }
}
```

### Loading States
```typescript
const isLoading = ref(true);

onMounted(async () => {
    isLoading.value = true;
    await store.fetchData();
    isLoading.value = false;
});
```
