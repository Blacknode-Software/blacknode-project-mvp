# AGENTS.md

## Introduction
This documentation provides clear guidelines for understanding and maintaining the structure and code of the `frontend/src` directory in the project. The file outlines the purpose and content of significant subdirectories and files to aid future agents better understand and maintain the project.

## Contents of Directories

### `main.ts`
This file serves as the main entry point for the Vue.js application. It is responsible for initializing the application and mounting it to the DOM, allowing the app to render and function properly.

**API Usage**:
  - The application initializes a Vue instance that serves as the root.
  - Libraries used:
    - **Pinia** for state management.
    - **Router** for managing application routes.

#### Implementation Guide:
1. Initialize the Vue instance by using the `createApp()` function.
2. Add plugins like Pinia or the Router to your app instance using the `app.use()` method.
3. Mount your application to the DOM node with `app.mount('#app');`.

---

### `assets`
This directory holds static files such as images, fonts, and stylesheets for the application.
- **Key Files**:
  - `main.css`: The main stylesheet for the app which imports global styles from `base.css`.
  - `logo.svg`: The logo for the app.

**Usage**:
  - Import CSS or other assets (e.g., images) from the `assets` directory into your components.

#### How to Add New Files:
1. Place new static assets (e.g., images, fonts, or additional stylesheets) into this folder.
2. Reference these files using relative paths in your code.

---

### `App.vue`
This is the main Vue single-file component serving as the base of the application. It defines the root template and global styles.

**API Usage**:
- The root `<template>` tag defines the overall application structure. A `<RouterView />` element allows routing between pages.

#### Implementation Guide:
1. Modify the `<template>` to adjust the global layout of the application.
2. Add global styles under the `<style scoped>` tag.
3. Use `<script setup>` for defining application-wide reactive variables and data bindings.

---

### `config/env.ts`
This file manages environment variables. It fetches the `API_URL` from the `.env` configuration files to allow dynamic API routing.

**Usage**:
- Use `API_URL` to interact with the backend API by referencing `import.meta.env.VITE_API_URL`.

#### Implementation Guide:
- Add more environment variables by extending the existing structure in this file.
- Add respective entries to the `env` files.

---

### `utils`
The `utils` directory contains reusable helper functions and modules to support common operations throughout the application.

**Key Files**:
- `lerp-color.ts`: A linear interpolator for hex colors.
- `result.ts`: Defines the `Result` interface and utility functions like:
  - `ok()`, `err()` (utility functions to generate result instances).
- `index.ts`: Handles re-exporting utility modules for easy imports.

#### Usage:
- Functions or modules from this directory can be imported directly. For example:
  ```typescript
  import { lerpColor } from '@/utils';
  ```
- Add any new helper utility functions in this directory, ensuring proper documentation and export.

---

### `views`
The `views` directory contains Vue single-file components representing different pages of the app.

**Key Example**: `LoginView.vue`
- Provides the authentication view for the app.
- Features integration with:
  - Vue router for navigation.
  - `auth-user` store for managing authentication data.
- Uses `reactive` and `ref` from Vue for data binding and form handling.
- Includes customized styles for the login page interface.

#### How to add views:
1. Create a `.vue` file in the `views` directory.
2. Use the single-file component structure (template, script, style).
3. Add the new view to the router configuration (found in `frontend/src/router`).

---