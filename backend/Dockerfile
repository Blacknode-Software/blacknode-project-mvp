# Builds only the API module and its dependencies, then runs the produced JAR

FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace

# Copy root and module POMs first to leverage layer cache for dependencies
COPY pom.xml ./
COPY blacknode-software-backend-api/pom.xml \
	blacknode-software-backend-application/pom.xml \
	blacknode-software-backend-domain/pom.xml \
	blacknode-software-backend-infrastructure/pom.xml \
	blacknode-software-backend-shared/pom.xml ./

# Copy all sources and build only the API module (and modules it depends on)
COPY . .
RUN mvn -B -DskipTests -pl blacknode-software-backend -am package -T 1C


FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app

# Copy the API module JAR from the build stage (wildcard handles version)
COPY --from=build /workspace/blacknode-software-backend-api/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

