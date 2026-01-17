# AGENTS.md - UI Toolkit Directory

## Overview
The `ui-toolkit` directory contains reusable, low-level UI components that serve as the building blocks for the application's user interface. These components are generic, highly reusable, and follow consistent design patterns.

## Directory Structure
- **CustomButton**: Customizable button component
- **PriorityText**: Text component for displaying task priority
- **ProgressBar**: Visual progress indicator component
- **TimestampDate**: Date/time display component for timestamps
- **__tests__**: Unit tests for UI components
- **index.ts**: Central export file for all UI toolkit components

## UI Toolkit Components

### CustomButton
A flexible button component with multiple variants:

**Features**:
- Multiple visual styles (primary, secondary, danger, etc.)
- Size variants (small, medium, large)
- Loading state support
- Disabled state
- Icon support
- Click event handling

**Props** (typical):
```typescript
{
    variant?: 'primary' | 'secondary' | 'danger' | 'ghost';
    size?: 'sm' | 'md' | 'lg';
    disabled?: boolean;
    loading?: boolean;
    icon?: string;
}
```

**Usage**:
```vue
<CustomButton 
    variant="primary" 
    size="md" 
    @click="handleClick"
>
    Save Changes
</CustomButton>
```

### PriorityText
Displays task priority with appropriate styling:

**Features**:
- Color-coded priority levels
- Text or badge display
- Consistent priority representation
- Accessibility support

**Props** (typical):
```typescript
{
    priority: 'low' | 'medium' | 'high' | 'critical';
    variant?: 'text' | 'badge';
}
```

**Usage**:
```vue
<PriorityText 
    :priority="task.priority" 
    variant="badge"
/>
```

### ProgressBar
Visual progress indicator:

**Features**:
- Percentage-based progress
- Customizable colors
- Smooth animations
- Size variants
- Label support

**Props** (typical):
```typescript
{
    value: number;      // 0-100
    max?: number;       // Default: 100
    color?: string;
    showLabel?: boolean;
    size?: 'sm' | 'md' | 'lg';
}
```

**Usage**:
```vue
<ProgressBar 
    :value="75" 
    color="#4CAF50" 
    showLabel 
/>
```

### TimestampDate
Formats and displays timestamps:

**Features**:
- Multiple format options
- Relative time display (e.g., "2 hours ago")
- Absolute time display
- Timezone handling
- Tooltip with full date

**Props** (typical):
```typescript
{
    timestamp: number | Date | UnixTimestamp;
    format?: 'relative' | 'absolute' | 'short' | 'long';
    showTooltip?: boolean;
}
```

**Usage**:
```vue
<TimestampDate 
    :timestamp="task.createdAt" 
    format="relative"
    showTooltip
/>
```

## Design Principles

### Reusability
- Components are generic and not tied to specific features
- Configurable through props
- Minimal business logic
- Focused single responsibility

### Consistency
- Shared design tokens (colors, spacing, typography)
- Consistent prop naming
- Standard event naming
- Uniform styling approach

### Accessibility
- Semantic HTML
- ARIA labels and roles
- Keyboard navigation
- Screen reader support
- Focus management

### Performance
- Lightweight components
- Efficient re-rendering
- No unnecessary watchers
- Optimized computed properties

## Component Structure

### Typical Component Template
```vue
<script setup lang="ts">
import { computed } from 'vue';

interface Props {
    variant?: 'default' | 'primary';
    size?: 'sm' | 'md' | 'lg';
}

const props = withDefaults(defineProps<Props>(), {
    variant: 'default',
    size: 'md',
});

const classes = computed(() => ({
    [`component--${props.variant}`]: true,
    [`component--${props.size}`]: true,
}));
</script>

<template>
    <div :class="classes">
        <slot />
    </div>
</template>

<style scoped>
.component {
    /* Base styles */
}

.component--primary {
    /* Variant styles */
}
</style>
```

## Styling Guidelines

### CSS Variables
Use CSS custom properties for theming:
```css
.custom-button {
    background-color: var(--color-primary);
    padding: var(--spacing-md);
    border-radius: var(--radius-base);
}
```

### Scoped Styles
- Always use scoped styles
- Avoid deep selectors unless necessary
- Use class-based styling
- Follow BEM-like naming for modifiers

### Responsive Design
- Components should be responsive by default
- Use relative units (rem, em, %)
- Test on multiple screen sizes
- Consider mobile-first approach

## Props and Events

### Prop Naming
- Use camelCase for multi-word props
- Boolean props should be prefixed (e.g., `showLabel`, `isActive`)
- Provide sensible defaults
- Use TypeScript interfaces

### Event Naming
- Use kebab-case for events
- Use descriptive names (e.g., `@item-selected`)
- Emit native DOM events when appropriate
- Document event payloads

### Slots
- Provide default slot for content
- Named slots for specific regions
- Scoped slots for data passing
- Document slot purposes

## Adding New UI Components

1. Create a new directory with the component name
2. Create the `.vue` file inside the directory
3. Implement component with TypeScript props
4. Add scoped styles
5. Export component in `index.ts`
6. Write unit tests in `__tests__`
7. Update this AGENTS.md file
8. Document props, events, and slots

## Component Testing

### Unit Tests
Test components with Vitest and Vue Test Utils:
```typescript
import { describe, it, expect } from 'vitest';
import { mount } from '@vue/test-utils';
import CustomButton from './CustomButton.vue';

describe('CustomButton', () => {
    it('renders button text', () => {
        const wrapper = mount(CustomButton, {
            slots: { default: 'Click me' }
        });
        expect(wrapper.text()).toBe('Click me');
    });
    
    it('emits click event', async () => {
        const wrapper = mount(CustomButton);
        await wrapper.trigger('click');
        expect(wrapper.emitted('click')).toBeTruthy();
    });
});
```

### Visual Testing
- Test different prop combinations
- Test responsive behavior
- Test accessibility features
- Test error states

## Using UI Toolkit Components

### In Application Components
```vue
<script setup lang="ts">
import { CustomButton, PriorityText, ProgressBar } from '@/ui-toolkit';

function handleSave() {
    // Save logic
}
</script>

<template>
    <div>
        <PriorityText :priority="task.priority" />
        <ProgressBar :value="task.completion" />
        <CustomButton @click="handleSave">
            Save Task
        </CustomButton>
    </div>
</template>
```

### Theming
Components should support theming through:
- CSS variables
- Props for colors/styles
- Class-based variants
- Design token system

## Best Practices

### Keep Components Small
- Single responsibility principle
- Compose complex UIs from simple components
- Extract repeated patterns
- Avoid overly complex components

### Prop Validation
- Use TypeScript for type safety
- Provide prop defaults
- Validate prop values when needed
- Document required props

### Documentation
- Add JSDoc comments to props
- Document events and their payloads
- Provide usage examples
- Explain complex behaviors

### Composition Over Configuration
- Prefer slots over many props
- Allow flexibility through composition
- Keep prop APIs simple
- Use sensible defaults

## Common Patterns

### Variant Pattern
```vue
<script setup lang="ts">
const props = defineProps<{
    variant?: 'primary' | 'secondary' | 'danger';
}>();
</script>

<template>
    <button :class="`btn btn--${variant}`">
        <slot />
    </button>
</template>
```

### Size Pattern
```vue
<script setup lang="ts">
const props = withDefaults(defineProps<{
    size?: 'sm' | 'md' | 'lg';
}>(), {
    size: 'md'
});
</script>
```

### Loading State
```vue
<script setup lang="ts">
const props = defineProps<{
    loading?: boolean;
}>();
</script>

<template>
    <button :disabled="loading">
        <span v-if="loading">Loading...</span>
        <slot v-else />
    </button>
</template>
```

## Dependencies
- Vue 3 (Composition API)
- TypeScript for type safety
- CSS for styling (scoped)
- Vue Test Utils for testing (dev)
- Vitest for test running (dev)

## Integration Points
- Used by components in `@/components`
- Used by views in `@/views`
- Used by layout components in `@/layout`
- Foundation for consistent UI/UX
