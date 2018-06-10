# image is based on OpenJDK image version 8-jdk-alpine
FROM openjdk:8-jdk-alpine

# install and update bash
RUN apk add --update bash

# copy target/universal/dist directory to /app directory in your image
COPY ./target/universal/play-table-of-contents-1.0-SNAPSHOT.zip /app

# make port 9000 visible
EXPOSE 9000

# run /app/bin/my-app executable in port 9000
CMD bash /dist/bin/play-table-of-contents -Dhttp.port=9000