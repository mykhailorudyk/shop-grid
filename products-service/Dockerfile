FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY . .

RUN mvn -pl products-service -am clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/products-service/target/*.jar app.jar

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "app.jar"]