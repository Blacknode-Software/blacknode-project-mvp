# Installation

This guide will walk you through installing Blacknode Software on your own infrastructure using Docker.

## Prerequisites

Before you begin, ensure your system meets the following requirements:

| Requirement | Minimum |
|-------------|---------|
| **Docker** | v20.10 or later |
| **Docker Compose** | v2.0 or later (or docker-compose v1.29+) |
| **RAM** | 2 GB |
| **Disk Space** | 10 GB |
| **OS** | Linux (Ubuntu 20.04+, Debian 11+, CentOS 8+) |

## Quick Install

The fastest way to get started is with our one-line installer. This script will:

1. Check for Docker and Docker Compose
2. Create the `blacknode-software` directory
3. Prompt you for your application URL
4. Generate secure secrets for JWT and database
5. Create the Docker Compose configuration
6. Start all services

### Run the Installer

Open your terminal and execute:

```bash
curl -fsSL https://docs.blacknode.software/public/install-on-premis-script.sh | bash
```

!!! tip "Custom Domain"
    When prompted for the Application URL, enter your domain (e.g., `https://blacknode.example.com`). For local testing, you can press Enter to use the default `http://localhost`.

## Manual Installation

If you prefer to install manually, follow these steps:

### 1. Create the Project Directory

```bash
mkdir blacknode-software && cd blacknode-software
```

### 2. Create the Environment File

Create a `.env` file with the following variables:

```bash
APPLICATION_URL=http://localhost
JWT_SECRET=your-secure-jwt-secret-here
POSTGRES_DB=blacknode
POSTGRES_USER=blacknode
POSTGRES_PASSWORD=your-secure-database-password
```

!!! warning "Security"
    Generate strong, random values for `JWT_SECRET` and `POSTGRES_PASSWORD`. You can use:
    ```bash
    openssl rand -hex 32
    ```

### 3. Create the Docker Compose File

Create a `docker-compose.yml` file:

```yaml
services:
  backend:
    image: blacknodesoftware/blacknode-backend:pre-release
    restart: unless-stopped
    environment:
      APPLICATION_URL: ${APPLICATION_URL}
      JWT_SECRET: ${JWT_SECRET}
      DB_URL: jdbc:postgresql://database:5432/${POSTGRES_DB}
      DB_USERNAME: ${POSTGRES_USER}
      DB_PASSWORD: ${POSTGRES_PASSWORD}
    expose:
      - "8080"
    depends_on:
      - database

  frontend:
    image: blacknodesoftware/blacknode-frontend:pre-release
    restart: unless-stopped
    expose:
      - "80"

  proxy:
    image: blacknodesoftware/blacknode-proxy:pre-release
    restart: unless-stopped
    depends_on:
      - backend
      - frontend
    ports:
      - "80:80"

  database:
    image: postgres:16
    restart: unless-stopped
    environment:
      POSTGRES_DB: ${POSTGRES_DB}
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
    volumes:
      - postgres_data:/var/lib/postgresql/data
    expose:
      - "5432"

volumes:
  postgres_data:
```

### 4. Start the Services

```bash
docker compose up -d
```

## Verify Installation

After installation, verify that all services are running:

```bash
docker compose ps
```

You should see all four services (`backend`, `frontend`, `proxy`, `database`) with status `Up`.

Open your browser and navigate to your application URL (default: `http://localhost`). You should see the Blacknode Software login page.

## Next Steps

- [Getting Started](../user-guide/getting-started.md) – Learn how to use Blacknode Software
- [Troubleshooting](troubleshooting.md) – Common issues and solutions
- [Uninstall](uninstall.md) – How to remove Blacknode Software
