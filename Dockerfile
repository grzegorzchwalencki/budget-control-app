#Build stage
FROM gradle:9.1.0-jdk25-corretto AS build
WORKDIR /usr/app/
COPY --chown=gradle:gradle . .
RUN gradle build -x test --no-daemon

# Package stage
FROM eclipse-temurin:25-jdk-ubi10-minimal
WORKDIR /usr/app/
RUN adduser -m appuser
USER appuser
COPY --from=build /usr/app//build/libs/budgetControl-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]