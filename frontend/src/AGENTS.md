# AGENTS.md

## Introduction
This documentation provides clear guidelines for understanding and maintaining the `main.ts` file, which serves as the entry point of the application.

## API Usage
- **Vue Instance Creation**: The application initializes a Vue instance that serves as the root of the application.
- **Libraries Used**:
  - **Pinia**: State management.
  - **Router**: For managing application routes.

## Implementation Guide
1. **Creating the Vue Instance**: 
   - This is done using `createApp()` from Vue to initialize the application.
2. **Mounting**:
   - The application is mounted to the DOM using `app.mount('#app')`. 
3. **Associated Libraries**:
   - Make sure to import and configure Pinia and Router before mounting the instance.

For further details refer to the Vue documentation for these libraries.