FROM openjdk:11.0.1-jre-slim-stretch
EXPOSE 8081
ARG JAR=ms-service-app-ms1.jar
COPY target/$JAR /app-ms1.jar
COPY opentelemetry-javaagent-1.33.6.jar /opentelemetry-javaagent.jar
ENTRYPOINT ["java", "-javaagent:/opentelemetry-javaagent.jar", "-jar","/app-ms1.jar"]