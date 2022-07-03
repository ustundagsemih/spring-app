FROM maven:3.8.6-jdk-11-slim as build
RUN mkdir /app
WORKDIR /app
COPY . /app
RUN mvn clean package

FROM openjdk:11.0.15-slim-buster
RUN mkdir /app
RUN addgroup --system appuser && adduser --system --shell=/bin/false --group appuser
COPY --from=build /app/target/app-0.0.1-final.jar /app/app-0.0.1-final.jar
WORKDIR /app
RUN chown -R appuser:appuser /app
USER appuser
CMD "java" "-jar" "app-0.0.1-final.jar"
