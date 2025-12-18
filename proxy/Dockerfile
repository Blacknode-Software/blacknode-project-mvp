FROM nginx:stable-alpine

# Copy the proxy config into the default site location
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose HTTP
EXPOSE 80