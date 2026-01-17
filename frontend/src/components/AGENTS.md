# AGENTS.md - Components Directory

## Overview
The `components` directory contains reusable Vue 3 components that are used throughout the Blacknode Project MVP application. These components follow the Composition API pattern with `<script setup>` syntax.

## Directory Structure
- **ChannelNavigation**: Components for navigating between channels
- **DialogCommon**: Common dialog/modal components shared across the app
- **FixedBackground**: Background component for consistent UI styling
- **InvitationDialog**: Dialog for handling user invitations
- **InvitesView**: Components for displaying and managing invites
- **KanbanColumn**: Kanban board column component for task management
- **LocalNavigation**: Local navigation components for specific views
- **MainSidebar**: Main sidebar navigation with organization and project navigation
- **NotificationsDialog**: Dialog for displaying notifications
- **TaskDialog**: Dialog for creating/editing tasks
- **TasksGroup**: Component for grouping and displaying tasks
- **__tests__**: Unit tests for components
- **index.ts**: Central export file for all components

## Component Architecture

### Component Pattern
Components in this directory follow these conventions:
- Use Vue 3 Composition API with `<script setup>` syntax
- TypeScript for type safety
- Props are defined using `defineProps<T>()`
- Emits are defined using `defineEmits<T>()`
- Scoped styles for component-specific styling

### Importing Components
Components are exported through `index.ts` for clean imports:
```typescript
import { MainSidebar, TaskDialog } from '@/components';
```

## Key Components

### MainSidebar
Main application sidebar containing:
- Organization navigation
- Project lists
- General navigation items

### TaskDialog
Modal for creating and editing tasks with:
- Task title and description
- Status selection
- Priority assignment
- Date/time settings

### KanbanColumn
Displays tasks in a vertical column for Kanban board view:
- Drag and drop support
- Task filtering by status
- Visual task cards

## Adding New Components

1. Create a new directory with the component name (PascalCase)
2. Create the `.vue` file inside the directory
3. Add TypeScript interfaces for props/emits
4. Export the component in `index.ts`
5. Add unit tests in `__tests__` directory if applicable

## Testing
Component tests are located in `__tests__` directory and use Vitest with Vue Test Utils.

## Dependencies
- Vue 3 (Composition API)
- TypeScript
- Pinia (for store access)
- Vue Router (for navigation)
