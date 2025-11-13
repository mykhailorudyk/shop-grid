FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY . .

RUN mvn -pl api-gateway -am clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY monitoring/otel/opentelemetry-javaagent.jar /app/otel-agent.jar
COPY --from=builder /app/api-gateway/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-javaagent:/app/otel-agent.jar", "-jar", "app.jar"]