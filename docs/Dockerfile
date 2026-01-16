FROM python:3.12-slim AS build

WORKDIR /app
RUN apt-get update \
	&& apt-get install -y --no-install-recommends git \
	&& rm -rf /var/lib/apt/lists/*
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt
COPY . .
RUN mkdocs build

FROM nginx:alpine
COPY --from=build /app/site /usr/share/nginx/html
