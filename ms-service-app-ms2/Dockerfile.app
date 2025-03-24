FROM openjdk:11.0.1-jre-slim-stretch
EXPOSE 8082
ARG JAR=ms-service-app-ms2.jar
COPY target/$JAR /app-ms2.jar
ENTRYPOINT ["java", "-jar","/app-ms2.jar"]