cd  D:\Site\labs\OpenTelemetry\hello-observability\hello-observability
mvn clean package
docker build -f Dockerfile -t rmendoza/hello-observability-manual .
cd  D:\Site\labs\OpenTelemetry\hello-observability\ms-service-app-ms1
mvn clean package
docker build -f Dockerfile -t  rmendoza/ms-service-app-ms1-manual .
cd  D:\Site\labs\OpenTelemetry\hello-observability\ms-service-app-ms2
mvn clean package
docker build -f Dockerfile -t  rmendoza/ms-service-app-ms2-manual .

