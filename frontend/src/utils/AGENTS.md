# AGENTS.md - Utils Directory

## Overview
The `utils` directory contains reusable utility functions and helper modules that provide common functionality across the application. These utilities are framework-agnostic and focus on pure logic.

## Directory Structure
- **lerp-color.ts**: Color interpolation utility for hex colors
- **result.ts**: Result type for functional error handling
- **unix-timestamp.ts**: Unix timestamp wrapper class
- **index.ts**: Central export file for all utilities

## Utility Modules

### lerp-color.ts
Linear interpolation for hex colors:

**Purpose**: Smoothly interpolate between two hex color values.

**Function**: `lerpColor(color1: string, color2: string, factor: number): string`
- `color1`: Starting hex color (e.g., '#FF0000')
- `color2`: Ending hex color (e.g., '#0000FF')
- `factor`: Interpolation factor (0.0 to 1.0)
- Returns: Interpolated hex color

**Use Cases**:
- Animating color transitions
- Creating color gradients
- Visual feedback based on progress
- Task priority color coding

**Example**:
```typescript
import { lerpColor } from '@/utils';

// 50% between red and blue
const purple = lerpColor('#FF0000', '#0000FF', 0.5);
// Result: '#7F007F'
```

### result.ts
Functional error handling with Result type:

**Purpose**: Type-safe error handling without exceptions.

**Type Definition**:
```typescript
interface Result<T, E> {
    isOk(): boolean;
    isErr(): boolean;
    value: T;      // Available when isOk() is true
    error: E;      // Available when isErr() is true
}
```

**Functions**:
- `ok<T>(value: T): Result<T, never>`: Create successful result
- `err<E>(error: E): Result<never, E>`: Create error result

**Use Cases**:
- API response handling
- Form validation
- File operations
- Any operation that can fail

**Example**:
```typescript
import { ok, err, type Result } from '@/utils';

function divide(a: number, b: number): Result<number, string> {
    if (b === 0) {
        return err('Division by zero');
    }
    return ok(a / b);
}

const result = divide(10, 2);
if (result.isOk()) {
    console.log('Result:', result.value); // 5
} else {
    console.error('Error:', result.error);
}
```

**Benefits**:
- Explicit error handling
- Type safety
- No uncaught exceptions
- Composable error handling

### unix-timestamp.ts
Unix timestamp wrapper class:

**Purpose**: Handle Unix timestamps with utility methods.

**Class**: `UnixTimestamp`
- Wraps Unix timestamp (seconds since epoch)
- Provides conversion and comparison methods
- Type-safe timestamp handling

**Common Methods** (typical implementation):
- `toDate()`: Convert to JavaScript Date
- `toISO()`: Convert to ISO string
- `isExpired()`: Check if timestamp is in the past
- `addSeconds(n)`: Add seconds to timestamp

**Use Cases**:
- Token expiration tracking
- Date/time calculations
- API timestamp handling
- Time-based features

**Example**:
```typescript
import { UnixTimestamp } from '@/utils';

const expiry = new UnixTimestamp(1609459200);
if (expiry.isExpired()) {
    console.log('Token has expired');
}
```

### index.ts
Central export hub:

**Purpose**: Provides a single import point for all utilities.

**Usage**:
```typescript
import { lerpColor, ok, err, UnixTimestamp } from '@/utils';
```

## Usage Guidelines

### When to Add Utilities
Add utilities when you have:
- Pure functions used in multiple places
- Complex logic that needs testing
- Framework-agnostic helper functions
- Type definitions used across modules

### When NOT to Add Utilities
Avoid adding utilities for:
- Vue-specific logic (use composables instead)
- Single-use functions
- Business logic (use stores)
- Component-specific helpers

## Utility Design Principles

### Pure Functions
- No side effects
- Same input always produces same output
- Easy to test
- Composable

### Type Safety
- Use TypeScript for type safety
- Export types alongside functions
- Provide generic types when applicable

### Documentation
- Add JSDoc comments for public functions
- Include usage examples
- Document edge cases
- Specify parameter constraints

### Testing
- Unit test all utility functions
- Test edge cases
- Test error conditions
- Aim for 100% coverage

## Adding New Utilities

1. Create a new `.ts` file for the utility
2. Implement pure functions with TypeScript types
3. Add JSDoc documentation
4. Export functions/classes
5. Add exports to `index.ts`
6. Write unit tests
7. Update this AGENTS.md file

## Utility Template

```typescript
/**
 * Brief description of what this utility does.
 * 
 * @param param1 - Description of first parameter
 * @param param2 - Description of second parameter
 * @returns Description of return value
 * 
 * @example
 * ```typescript
 * const result = utilityFunction('input', 42);
 * console.log(result); // expected output
 * ```
 */
export function utilityFunction(param1: string, param2: number): string {
    // Implementation
    return `${param1}: ${param2}`;
}
```

## Common Utility Categories

### String Utilities
- Formatting
- Parsing
- Validation
- Sanitization

### Number Utilities
- Calculations
- Formatting
- Validation
- Conversions

### Date/Time Utilities
- Formatting
- Parsing
- Calculations
- Comparisons

### Array Utilities
- Transformations
- Filtering
- Sorting
- Aggregations

### Object Utilities
- Deep cloning
- Merging
- Property access
- Validation

## Best Practices

### Naming Conventions
- Use descriptive names (e.g., `formatCurrency`, not `fc`)
- Use verbs for functions (e.g., `calculateTotal`)
- Use nouns for classes (e.g., `UnixTimestamp`)

### Error Handling
- Use Result type for operations that can fail
- Throw exceptions only for programmer errors
- Provide helpful error messages

### Performance
- Avoid premature optimization
- Profile before optimizing
- Cache expensive calculations
- Use memoization when appropriate

### Dependencies
- Minimize external dependencies
- Keep utilities lightweight
- Avoid framework coupling
- Prefer pure JavaScript/TypeScript

## Testing Utilities

### Unit Tests
```typescript
import { describe, it, expect } from 'vitest';
import { lerpColor } from './lerp-color';

describe('lerpColor', () => {
    it('interpolates colors correctly', () => {
        const result = lerpColor('#FF0000', '#0000FF', 0.5);
        expect(result).toBe('#7F007F');
    });
    
    it('returns first color at factor 0', () => {
        const result = lerpColor('#FF0000', '#0000FF', 0);
        expect(result).toBe('#FF0000');
    });
});
```

## Integration Points
- Used throughout the application
- Imported by components, stores, and services
- Foundation for type-safe error handling
- Shared types and interfaces

## Dependencies
- TypeScript for type safety
- No runtime dependencies (pure JavaScript)
- Vitest for testing (dev dependency)
