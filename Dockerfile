FROM openjdk:11.0-jre-slim
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY ./build/libs/falcon-data-pipeline.jar ./data-pipeline.jar
EXPOSE 8080
CMD ["java","-jar","data-pipeline.jar"]
