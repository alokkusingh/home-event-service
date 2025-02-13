home-event-service
==================
Server Sent Event (SSE)

### How to run
````
java -jar target/home-event-service-1.0.0.jar
````

#### Build
1. Maven Package
   ```shell
   mvn clean package -DskipTests
   ```
2. Docker Build, Push & Run
   ```shell
   docker build -t alokkusingh/home-event-service:latest -t alokkusingh/home-event-service:1.0.0 --build-arg JAR_FILE=target/home-event-service-1.0.0.jar .
   ```
   ```shell
   docker push alokkusingh/home-event-service:latest
   ```
   ```shell
   docker push alokkusingh/home-event-service:1.0.0
   ```
   ```shell
   docker run -d -p 8081:8081 --rm --name home-event-service alokkusingh/home-event-service 
   ```
### Test
```shell
curl -X GET -location 'http://localhost:8081/home/event/123/subscribe'
```