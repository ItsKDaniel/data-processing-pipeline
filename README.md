# data-processing-pipeline

### Overview

The application publishes data to a Redis Pub/Sub topic. The data is also broadcast to a websocket for any clients
listening. The data published to the topic is retrieved and persisted into Redis.

The app has swagger enabled. Launch `/swagger-ui.html` on running the app to view the documentation.

### Development requirements

1. JDK 11
2. Gradle

### To run in local

1. Checkout the app and build `grade clean build`
2. The project has a `docker-compose.yml` file. Use this to build and deploy the app to docker.
   ```bash
   docker-compose build
   docker-compose up
   ```

### API Responses

All REST API response follow the structure

```json
{
  "code": "",
  "content": {}
}
```

However, the `GET /payload/v1/all` returns a list `[]` as per the requirements
