 
 


PARA LA UTILOZACION DE LA OPCION AUTO-ISNTRUMENTACION (sin apm) Y MANUAL(con apm)

cd  D:\development\repositorios\Observability\OpenTelemetry\hello-observability\hello-observability
mvn clean package
docker build -f Dockerfile.app -t rmendoza/hello-observability-simple:v2 .
docker build -f Dockerfile -t rmendoza/hello-observability-manual:v2 .
docker build -f Dockerfile.appv3 -t rmendoza/hello-observability-manual:v3 .

cd  D:\development\repositorios\Observability\OpenTelemetry\hello-observability\ms-service-app-ms1
mvn clean package
docker build -f Dockerfile.app -t  rmendoza/ms-service-app-ms1-simple:v2 .
docker build -f Dockerfile -t  rmendoza/ms-service-app-ms1-manual:v2 .
docker build -f Dockerfile.appv3 -t  rmendoza/ms-service-app-ms1-manual:v3 .

cd  D:\development\repositorios\Observability\OpenTelemetry\hello-observability\ms-service-app-ms2 
mvn clean package
docker build -f Dockerfile.app -t  rmendoza/ms-service-app-ms2-simple:v2 .
docker build -f Dockerfile -t  rmendoza/ms-service-app-ms2-manual:v2 .
docker build -f Dockerfile.appv3 -t  rmendoza/ms-service-app-ms2-manual:v3 .
