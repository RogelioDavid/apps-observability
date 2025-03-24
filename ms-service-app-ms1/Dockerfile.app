FROM openjdk:11.0.1-jre-slim-stretch
EXPOSE 8081
ARG JAR=ms-service-app-ms1.jar
COPY target/$JAR /app-ms1.jar
ENTRYPOINT ["java", "-jar","/app-ms1.jar"]