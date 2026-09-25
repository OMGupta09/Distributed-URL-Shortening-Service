FROM maven:3.9.16-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn

RUN mvn -B -DskipTests dependency:go-offline

COPY src src

RUN mvn -B -DskipTests clean package


FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 10000

ENTRYPOINT ["java", "-jar", "app.jar"]