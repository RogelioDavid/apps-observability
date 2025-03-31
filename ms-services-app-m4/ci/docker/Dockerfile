FROM openjdk:17-jdk-slim
EXPOSE 8080
WORKDIR /api
ARG JAR=ms-service-app-ms4.jar
COPY build/libs/*.jar /app.jar
COPY opentelemetry-javaagent-1.33.6.jar /opentelemetry-javaagent.jar
ENTRYPOINT ["java",  "-javaagent:/opentelemetry-javaagent.jar", "-jar","/app.jar"]