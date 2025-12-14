<img width="1676" height="946" alt="image" src="https://github.com/user-attachments/assets/a3d3cbdb-ab3e-4843-82b4-6ecf3751219e" />


Blacknode is a lightweight, web-based project management system designed for small and medium-sized teams working with **Scrum** and **Kanban** methodologies. The project focuses on clarity, usability, and on-premise deployment, avoiding the complexity and cost of large SaaS tools while offering more structure than minimal task boards.

## Purpose

The system addresses common problems in team-based project work:
- poor visibility of progress,
- fragmented communication,
- overly complex or expensive existing tools.

Blacknode provides a single, coherent environment for planning, execution, and monitoring of projects.

## Key Features

- Project and task management
- Kanban boards and Scrum sprints
- Role-based access control (Admin, Project Manager, Lead, Member)
- Real-time notifications and comments
- Progress and workload reporting
- Clear separation of responsibilities and permissions
- On-premise, single-instance deployment

## Architecture

Blacknode is built using a client–server architecture:

- **Frontend:** Vue.js, Pinia, CSS  
- **Backend:** Java, Spring Boot (DDD-oriented structure)  
- **Database:** SurrealDB  
- **Communication:** REST API, WebSocket (real-time features)  
- **Infrastructure:** Docker / Docker Compose  
- **File storage:** S3-compatible storage (optional)

## Repository Structure (high level)

- `frontend/` – user interface, state management, UI components
- `backend/` – domain logic, application services, REST controllers
- `docs/` – projects documentation site
- `docker-compose.yml` – local and deployment setup  

## Setup (overview)

1. Clone the repository.
2. Configure environment variables for backend, database, and optional S3 storage.
3. Start the system using Docker Compose or run frontend and backend separately in development mode.
4. Access the application via a web browser.

Detailed setup instructions can be found [here](https://docs.blacknode.software).

## Status

The project targets delivery of a functional **MVP**.  
Scope is intentionally limited to core project-management features; financial modules, HR management, SaaS integrations, and multi-instance scaling are out of scope.
