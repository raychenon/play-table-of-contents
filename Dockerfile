# image is based on OpenJDK image version 8-jdk-alpine
FROM openjdk:8-jdk-alpine

# install and update bash
RUN apk add --update bash

# copy target/universal/dist directory to /app directory in your image
COPY ./target/universal/dist /app

# make port 8080 visible
EXPOSE 8080

# run /app/bin/my-app executable in port 8080
CMD bash /app/bin/app -Dhttp.port=8080