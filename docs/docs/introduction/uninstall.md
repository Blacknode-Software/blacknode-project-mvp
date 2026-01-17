# Uninstall

This guide explains how to remove Blacknode Software from your system.

## Before You Begin

!!! warning "Data Loss"
    Uninstalling Blacknode Software will permanently delete all your data, including projects, tasks, and user accounts. Make sure to backup any important data before proceeding.

## Stop and Remove Containers

Navigate to your Blacknode Software directory and stop all services:

```bash
cd blacknode-software
docker compose down
```

This will stop and remove the containers, but preserve your data volumes.

## Remove Data Volumes

To completely remove all data, including the PostgreSQL database:

```bash
docker compose down -v
```

!!! danger "Irreversible Action"
    The `-v` flag removes all associated volumes. This action cannot be undone and all data will be permanently lost.

## Remove Docker Images

To free up disk space, remove the Blacknode Software images:

```bash
docker rmi blacknodesoftware/blacknode-backend:pre-release
docker rmi blacknodesoftware/blacknode-frontend:pre-release
docker rmi blacknodesoftware/blacknode-proxy:pre-release
```

Optionally, remove the PostgreSQL image if no longer needed:

```bash
docker rmi postgres:16
```

## Remove Installation Directory

Finally, remove the Blacknode Software directory:

```bash
cd ..
rm -rf blacknode-software
```

## Complete Uninstall Command

For a complete uninstall in one command:

```bash
cd blacknode-software && \
docker compose down -v && \
docker rmi blacknodesoftware/blacknode-backend:pre-release \
           blacknodesoftware/blacknode-frontend:pre-release \
           blacknodesoftware/blacknode-proxy:pre-release && \
cd .. && rm -rf blacknode-software
```

## Verify Removal

Confirm that all Blacknode Software containers and volumes have been removed:

```bash
# Check for remaining containers
docker ps -a | grep blacknode

# Check for remaining volumes
docker volume ls | grep blacknode

# Check for remaining images
docker images | grep blacknode
```

If any of these commands return results, you can manually remove them using `docker rm`, `docker volume rm`, or `docker rmi` respectively.

---

!!! info "Reinstalling"
    Want to start fresh? Head back to the [Installation](installation.md) guide to reinstall Blacknode Software.
