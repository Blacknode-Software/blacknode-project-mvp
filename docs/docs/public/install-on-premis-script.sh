#!/usr/bin/env bash
set -e

BASE_DIR="blacknode-software"
ENV_FILE="$BASE_DIR/.env"
COMPOSE_FILE="$BASE_DIR/docker-compose.yml"

# ---- checks ----
if ! command -v docker >/dev/null 2>&1; then
  echo "Docker is not installed or not in PATH."
  exit 1
fi

if docker compose version >/dev/null 2>&1; then
  DOCKER_COMPOSE_CMD="docker compose"
elif command -v docker-compose >/dev/null 2>&1; then
  DOCKER_COMPOSE_CMD="docker-compose"
else
  echo "docker-compose is not available (neither v2 nor v1)."
  exit 1
fi

# ---- utils ----
generate_secret() {
  if command -v openssl >/dev/null 2>&1; then
    openssl rand -hex 32
  elif command -v base64 >/dev/null 2>&1; then
    head -c 48 /dev/urandom | base64 | tr -d '\n' | head -c 64
  else
    hexdump -n 32 -e '4/4 "%08X"' /dev/urandom
  fi
}

# ---- setup ----
mkdir -p "$BASE_DIR"

read -r -p "Application URL [http://localhost]: " APPLICATION_URL
APPLICATION_URL=${APPLICATION_URL:-http://localhost}

JWT_SECRET=$(generate_secret)
POSTGRES_DB="blacknode"
POSTGRES_USER="blacknode"
POSTGRES_PASSWORD=$(generate_secret | head -c 32)

cat > "$ENV_FILE" <<EOF
APPLICATION_URL=$APPLICATION_URL
JWT_SECRET=$JWT_SECRET
POSTGRES_DB=$POSTGRES_DB
POSTGRES_USER=$POSTGRES_USER
POSTGRES_PASSWORD=$POSTGRES_PASSWORD
EOF

cat > "$COMPOSE_FILE" <<'EOF'
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
EOF

# ---- run ----
cd "$BASE_DIR"
$DOCKER_COMPOSE_CMD up -d

echo "Blacknode Software has been installed and started successfully."