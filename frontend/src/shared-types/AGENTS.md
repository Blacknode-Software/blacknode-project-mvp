# AGENTS.md - Shared Types Directory

## Overview
The `shared-types` directory contains TypeScript type definitions and interfaces that are shared across the application. These types define the data structures used throughout the frontend and should match backend API contracts.

## Directory Structure
- **auth-user.ts**: Authentication and user-related types
- **channel.ts**: Channel/project channel types
- **invite.ts**: User invitation types
- **organization.ts**: Organization types
- **project.ts**: Project types
- **task-status.ts**: Task status enumeration and types
- **task.ts**: Task-related types
- **index.ts**: Central export file for all types

## Type Files

### auth-user.ts
Authentication and user types:

**Types** (typical):
```typescript
interface User {
    id: string;
    email: string;
    name: string;
    avatar?: string;
    createdAt: number;
}

interface AuthToken {
    accessToken: string;
    accessTokenExpiresAt: number;
    refreshToken?: string;
}

interface AuthUser extends User {
    organizations: string[];
    permissions: Permission[];
}
```

**Usage**: Stores, API services, components dealing with authentication

### channel.ts
Channel (project channel) types:

**Types** (typical):
```typescript
interface Channel {
    id: string;
    name: string;
    description?: string;
    organizationId: string;
    projectId: string;
    createdAt: number;
    updatedAt: number;
}

interface ChannelMember {
    userId: string;
    channelId: string;
    role: 'admin' | 'member' | 'viewer';
    joinedAt: number;
}

interface ChannelSettings {
    isPublic: boolean;
    allowInvites: boolean;
    defaultTaskStatus: string;
}
```

**Usage**: Channel management, navigation, task context

### invite.ts
User invitation types:

**Types** (typical):
```typescript
interface Invite {
    id: string;
    email: string;
    organizationId: string;
    channelId?: string;
    role: 'admin' | 'member' | 'viewer';
    status: 'pending' | 'accepted' | 'rejected' | 'expired';
    invitedBy: string;
    createdAt: number;
    expiresAt: number;
}

interface InviteRequest {
    email: string;
    role: string;
    organizationId: string;
    channelId?: string;
}
```

**Usage**: Invitation dialogs, member management

### organization.ts
Organization types:

**Types** (typical):
```typescript
interface Organization {
    id: string;
    name: string;
    description?: string;
    logo?: string;
    createdAt: number;
    updatedAt: number;
}

interface OrganizationMember {
    userId: string;
    organizationId: string;
    role: 'owner' | 'admin' | 'member';
    joinedAt: number;
}

interface OrganizationSettings {
    allowPublicProjects: boolean;
    defaultMemberRole: string;
}
```

**Usage**: Organization context, member management, settings

### project.ts
Project types:

**Types** (typical):
```typescript
interface Project {
    id: string;
    name: string;
    description?: string;
    organizationId: string;
    createdAt: number;
    updatedAt: number;
}

interface ProjectMember {
    userId: string;
    projectId: string;
    role: 'admin' | 'member';
    joinedAt: number;
}
```

**Usage**: Project lists, project navigation, project management

### task-status.ts
Task status enumeration:

**Types** (typical):
```typescript
enum TaskStatus {
    TODO = 'todo',
    IN_PROGRESS = 'in_progress',
    IN_REVIEW = 'in_review',
    DONE = 'done',
    BLOCKED = 'blocked',
}

interface StatusColumn {
    status: TaskStatus;
    label: string;
    color: string;
    order: number;
}
```

**Usage**: Task management, Kanban board, task filters

### task.ts
Task types:

**Types** (typical):
```typescript
interface Task {
    id: string;
    title: string;
    description?: string;
    status: TaskStatus;
    priority: 'low' | 'medium' | 'high' | 'critical';
    assigneeId?: string;
    channelId: string;
    createdBy: string;
    createdAt: number;
    updatedAt: number;
    dueDate?: number;
    completedAt?: number;
}

interface TaskCreateRequest {
    title: string;
    description?: string;
    priority?: string;
    channelId: string;
    assigneeId?: string;
    dueDate?: number;
}

interface TaskUpdateRequest {
    title?: string;
    description?: string;
    status?: TaskStatus;
    priority?: string;
    assigneeId?: string;
    dueDate?: number;
}
```

**Usage**: Task lists, Kanban board, task dialogs, task API services

### index.ts
Central export hub:

**Purpose**: Provides single import point for all shared types.

**Usage**:
```typescript
import type { Task, TaskStatus, Organization, User } from '@/shared-types';
```

## Type Conventions

### Naming Patterns

#### Entities
- Use singular nouns: `Task`, `User`, `Organization`
- PascalCase for interfaces/types
- Represent core domain objects

#### Request/Response Types
- Suffix with purpose: `TaskCreateRequest`, `TaskUpdateRequest`
- Match API endpoint expectations
- Separate input from output types

#### Enums
- PascalCase for enum name: `TaskStatus`
- UPPER_SNAKE_CASE for enum values: `IN_PROGRESS`
- Use when values are fixed set

### Optional vs Required
- Use `?` for optional properties
- Be explicit about what's required
- Match backend API requirements
- Consider validation needs

### Timestamps
- Use Unix timestamps (number) for consistency
- Name with `At` suffix: `createdAt`, `updatedAt`
- Consider using `UnixTimestamp` wrapper from utils

## Using Shared Types

### In Components
```vue
<script setup lang="ts">
import type { Task, TaskStatus } from '@/shared-types';
import { ref } from 'vue';

const task = ref<Task>({
    id: '1',
    title: 'Example task',
    status: TaskStatus.TODO,
    priority: 'medium',
    channelId: 'ch1',
    createdBy: 'user1',
    createdAt: Date.now(),
    updatedAt: Date.now(),
});
</script>
```

### In Stores
```typescript
import { defineStore } from 'pinia';
import type { Task } from '@/shared-types';
import { ref } from 'vue';

export const useTasksStore = defineStore('tasks', () => {
    const tasks = ref<Task[]>([]);
    
    function addTask(task: Task) {
        tasks.value.push(task);
    }
    
    return { tasks, addTask };
});
```

### In API Services
```typescript
import type { Task, TaskCreateRequest } from '@/shared-types';

async function createTask(payload: TaskCreateRequest): Promise<Result<Task, ApiError>> {
    // API call implementation
}
```

## Type Safety Benefits

### Compile-Time Checking
- Catch errors before runtime
- IDE autocomplete support
- Refactoring safety
- Documentation through types

### API Contract Enforcement
- Ensure frontend matches backend
- Prevent data shape mismatches
- Type-safe API calls
- Reduce runtime errors

## Adding New Types

1. Identify the domain or entity
2. Create a new `.ts` file with appropriate name
3. Define interfaces/types with JSDoc comments
4. Consider request/response variants
5. Export types
6. Add exports to `index.ts`
7. Update this AGENTS.md file
8. Coordinate with backend API contracts

## Type Definition Template

```typescript
/**
 * Represents a [entity name] in the system.
 */
export interface EntityName {
    /**
     * Unique identifier for the entity.
     */
    id: string;
    
    /**
     * The name of the entity.
     */
    name: string;
    
    /**
     * Optional description.
     */
    description?: string;
    
    /**
     * Unix timestamp of creation.
     */
    createdAt: number;
}

/**
 * Request payload for creating a new entity.
 */
export interface EntityCreateRequest {
    name: string;
    description?: string;
}

/**
 * Request payload for updating an entity.
 */
export interface EntityUpdateRequest {
    name?: string;
    description?: string;
}
```

## Best Practices

### Keep Types Focused
- One file per domain entity
- Related types in same file
- Don't mix unrelated types
- Maintain clear boundaries

### Avoid Type Duplication
- Reuse types across modules
- Use utility types (Pick, Omit, Partial)
- Extend base types when appropriate
- Don't duplicate API types

### Documentation
- Add JSDoc comments to types
- Document non-obvious properties
- Explain constraints
- Provide examples

### Sync with Backend
- Types should match API contracts
- Coordinate schema changes
- Validate against OpenAPI spec if available
- Use code generation if possible

## Utility Types

### TypeScript Utility Types
Leverage built-in utility types:
```typescript
// Pick specific properties
type TaskPreview = Pick<Task, 'id' | 'title' | 'status'>;

// Make all properties optional
type PartialTask = Partial<Task>;

// Make all properties required
type RequiredTask = Required<Task>;

// Omit properties
type TaskWithoutDates = Omit<Task, 'createdAt' | 'updatedAt'>;
```

### Custom Utility Types
```typescript
// ID types for type safety
type TaskId = string & { readonly brand: unique symbol };
type UserId = string & { readonly brand: unique symbol };

// Result type from utils
type TaskResult = Result<Task, ApiError>;
```

## Type Validation

### Runtime Validation
Types don't exist at runtime. Consider:
- Zod for schema validation
- Manual validation functions
- Type guards for narrowing

```typescript
function isTask(obj: any): obj is Task {
    return (
        typeof obj === 'object' &&
        typeof obj.id === 'string' &&
        typeof obj.title === 'string' &&
        typeof obj.status === 'string'
    );
}
```

## Dependencies
- TypeScript for type definitions
- No runtime dependencies (types are erased)
- Shared with backend (ideally)

## Integration Points
- Used throughout entire application
- Components, stores, services, utils
- Provides type safety across boundaries
- Foundation for IDE support and refactoring
