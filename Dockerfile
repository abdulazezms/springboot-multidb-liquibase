FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY ./src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/multi-dbs-0.0.1-SNAPSHOT.jar app.jar

RUN addgroup --system appuser && adduser -S -s /usr/sbin/nologin -G appuser appuser

RUN chown -R appuser:appuser /app

USER appuser

ENTRYPOINT ["java", "-jar", "app.jar"]
