FROM openjdk:11.0.1-jre-slim-stretch
EXPOSE 8080
ARG JAR=hello-observability.jar
COPY target/$JAR /app.jar
COPY opentelemetry-javaagent-1.33.6.jar /opentelemetry-javaagent.jar
ENTRYPOINT ["java", "-javaagent:/opentelemetry-javaagent.jar", "-jar","/app.jar"]