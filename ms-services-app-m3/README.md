docker build -f Dockerfile.simple -t rmendoza/user-observability-simple:v1.0 .

docker build -f Dockerfile.manual -t rmendoza/user-observability-manual:v1.0 .

docker run -p 3000:3000 rmendoza/user-observability-simple:v1.0

docker run -p 3000:3000 rmendoza/user-observability-manual:v1.0

 



docker run   rmendoza/user-observability-simple:v1.1