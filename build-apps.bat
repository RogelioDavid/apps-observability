cd  D:\Site\labs\OpenTelemetry\hello-observability\hello-observability
mvn clean package
docker build -f Dockerfile.app -t rmendoza/hello-observability-simple .
cd  D:\Site\labs\OpenTelemetry\hello-observability\ms-service-app-ms1
mvn clean package
docker build -f Dockerfile.app -t  rmendoza/ms-service-app-ms1-simple .
cd  D:\Site\labs\OpenTelemetry\hello-observability\ms-service-app-ms2
mvn clean package
docker build -f Dockerfile.app -t  rmendoza/ms-service-app-ms2-simple .
