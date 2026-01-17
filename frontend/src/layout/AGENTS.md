# AGENTS.md - Layout Directory

## Overview
The `layout` directory contains high-level layout components that define the structure and shell of the application. These components wrap around views and provide consistent layout patterns.

## Directory Structure
- **MainHeader**: Top navigation header component
- **MainView**: Primary application layout wrapper
- **UniDialog**: Universal dialog/modal component
- **UniSidebar**: Universal sidebar component
- **__tests__**: Unit tests for layout components
- **index.ts**: Central export file for layout components

## Layout Components

### MainView
The main application layout wrapper that:
- Provides the overall page structure
- Combines header, sidebar, and content areas
- Manages responsive layout behavior
- Serves as the container for routed views

### MainHeader
Top navigation bar that includes:
- Application branding/logo
- User profile access
- Quick actions
- Notification indicators

### UniDialog
A universal dialog component for:
- Consistent modal styling
- Backdrop handling
- Accessibility features
- Keyboard navigation (ESC to close)

### UniSidebar
A flexible sidebar component for:
- Navigation menus
- Collapsible sections
- Responsive behavior
- Consistent styling

## Layout Patterns

### Composition
Layout components are designed to be composed together:
```vue
<MainView>
  <template #header>
    <MainHeader />
  </template>
  <template #sidebar>
    <UniSidebar />
  </template>
  <template #content>
    <RouterView />
  </template>
</MainView>
```

### Slots
Layout components use slots for flexibility:
- Named slots for specific regions (header, sidebar, content)
- Default slots for simple use cases
- Scoped slots when passing data is needed

## Usage Guidelines

### When to Use Layout Components
- For page-level structure and organization
- To maintain consistent UI patterns across views
- When building new top-level pages

### When to Use Regular Components
- For reusable UI elements within content areas
- For widgets and interactive elements
- For smaller, focused functionality

## Styling
- Layout components use scoped styles
- They define structural CSS (flexbox/grid layouts)
- They set consistent spacing and sizing
- Individual content styling is left to child components

## Adding New Layout Components

1. Create a new directory with the component name
2. Create the `.vue` file with layout logic
3. Define slots for flexible content areas
4. Export the component in `index.ts`
5. Document usage patterns in this file
6. Add tests in `__tests__` if applicable

## Testing
Layout component tests focus on:
- Slot rendering
- Responsive behavior
- Accessibility
- Component composition

## Dependencies
- Vue 3 (Composition API)
- Vue Router (for MainView)
- Component imports from `@/components`
