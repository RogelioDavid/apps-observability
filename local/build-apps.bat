cd  D:\Site\labs\OpenTelemetry\hello-observability\hello-observability
mvn clean package
docker build -f Dockerfile  -t hello-observability .
cd  D:\Site\labs\OpenTelemetry\hello-observability\ms-service-app-ms1
mvn clean package
docker build -f Dockerfile  -t  ms-service-app-ms1 .
cd  D:\Site\labs\OpenTelemetry\hello-observability\ms-service-app-ms2
mvn clean package
docker build -f Dockerfile  -t  ms-service-app-ms2 .
