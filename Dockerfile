#Build stage
FROM gradle:jdk17-corretto AS build
WORKDIR /usr/app/
COPY --chown=gradle:gradle . .
RUN gradle build -x test --no-daemon

# Package stage
FROM eclipse-temurin:17-jre-ubi9-minimal
WORKDIR /usr/app/
RUN adduser -m appuser
USER appuser
COPY --from=build /usr/app//build/libs/budgetControl-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]