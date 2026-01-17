FROM node:20-alpine AS builder
WORKDIR /app

# Copy lockfile first when present to leverage layer caching
COPY package*.json ./

# Install dependencies (prefer ci when lockfile exists)
RUN if [ -f package-lock.json ]; then npm ci --silent; else npm install --silent; fi

# Copy source and build
COPY . .
RUN npm run build

FROM nginx:stable-alpine AS production

# Replace default config with a minimal SPA-aware config
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy built assets
COPY --from=builder /app/dist /usr/share/nginx/html

EXPOSE 80
STOPSIGNAL SIGTERM
CMD ["nginx", "-g", "daemon off;"]

