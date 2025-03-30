FROM ubuntu:latest
LABEL authors="anvud"

FROM openjdk:17-jdk-slim

ENV APP_HOME=/app
WORKDIR $APP_HOME

COPY target/AnVD_project-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]