# AGENTS.md - API Services Directory

## Overview
The `api-services` directory contains service modules that handle all HTTP communication with the backend API. These services provide typed interfaces for API calls and consistent error handling.

## Directory Structure
- **authentication.ts**: Authentication and login services
- **channels.ts**: Channel management API calls
- **invites.ts**: User invitation services
- **members.ts**: Organization/channel member management
- **organizations.ts**: Organization CRUD operations
- **projects.ts**: Project management services
- **setup.ts**: Initial application setup services
- **tasks.ts**: Task CRUD and management operations
- **utils**: Utility functions for API services
- **index.ts**: Central export file for all services

## Architecture

### Service Pattern
API services follow a consistent pattern:
```typescript
export const useServiceName = defineApiService(API_URL, {
    async methodName(baseUrl, payload): Promise<Result<Success, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/endpoint`, {
                method: 'POST',
                body: JSON.stringify(payload),
                headers: {
                    'Content-Type': 'application/json',
                },
            }),
        );
    },
});
```

### Key Concepts

#### Result Type
All API methods return a `Result<Success, ApiError>` type:
- `Result.ok()`: Successful response with typed data
- `Result.err()`: Error response with error details
- Enables type-safe error handling without exceptions

#### defineApiService
A utility function that:
- Creates service instances with base URL configuration
- Provides consistent service structure
- Enables dependency injection for testing

#### parseResponse
Handles fetch response parsing:
- Converts HTTP responses to Result types
- Parses JSON response bodies
- Handles network errors
- Provides typed error information

## Using API Services

### In Stores (Recommended)
```typescript
import { useAuthenticationApiService } from '@/api-services/authentication';

export const useAuthUserStore = defineStore('authUser', () => {
    const apiService = useAuthenticationApiService();
    
    async function login(email: string, password: string) {
        const res = await apiService.requestAuthentication({ email, password });
        
        if (res.isOk()) {
            // Handle success
            const { accessToken } = res.value;
        } else {
            // Handle error
            const error = res.error;
        }
    }
});
```

### In Components (Use with Caution)
Direct API service usage in components should be rare. Prefer using stores for state management and API calls.

## Service Files

### authentication.ts
- `requestAuthentication(email, password)`: Login endpoint
- Returns access token and expiration

### channels.ts
- Channel CRUD operations
- Channel member management
- Channel settings

### tasks.ts
- Task creation, reading, updating, deletion
- Task status changes
- Task assignment

### organizations.ts
- Organization management
- Organization member operations
- Organization settings

### projects.ts
- Project CRUD operations
- Project-channel associations

### invites.ts
- User invitation creation
- Invitation acceptance/rejection
- Invitation listing

### members.ts
- Member management across organizations and channels
- Role assignments
- Member removal

## Adding New API Services

1. Create a new `.ts` file for the service domain
2. Import `defineApiService`, `parseResponse`, and types
3. Define TypeScript interfaces for request/response types
4. Implement service methods following the pattern
5. Export service using `useServiceName` naming convention
6. Add exports to `index.ts`

## Error Handling

### API Errors
The `ApiError` type includes:
- HTTP status code
- Error message
- Additional error details

### Network Errors
Network failures are caught and converted to ApiError format.

## Authentication

### Access Tokens
Services that require authentication should:
- Accept access token as parameter, or
- Retrieve token from auth store
- Include token in Authorization header

Example:
```typescript
headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${accessToken}`,
}
```

## Testing
- Service tests should mock fetch calls
- Test both success and error paths
- Verify request formatting
- Validate response parsing

## Configuration
- Base URL is imported from `@/config/env`
- Uses `VITE_API_URL` environment variable
- Can be overridden for testing

## Dependencies
- `@/config/env`: API URL configuration
- `@/utils`: Result type definition
- Native Fetch API for HTTP requests
